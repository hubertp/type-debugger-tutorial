---
layout: page
title: "More examples"
---
{% include JB/setup %}

Examples in the [introduction]({{BASE_PATH}}tutorial.md) did not go into much detail on what type debugger was showing. In this section we will give a more involved description in a form of a walk-through. Users do not have to follow every example in sequence and therefore can stop/start at any point.

## Debugging simple subtyping ##

**Subtyping01.scala** contains a couple of simple examples that involve subtype checking for assignments and application.

#### Typechecking `method test01`
Let's select `method test01` and focus on the definition of `var c`. Typechecking goal contains a separate typing goal (that by now we should be familiar with) and an adaptation goal `Can we adapt expression to the expected type Base?`. The latter's immediate subgoal only says that the subtyping constraint succeeded so let's expand the hidden goals.

The conformance test in this particular case is actually pretty trivial - it just gets a base type of `A` wrt to `Base` which is `Base` itself and the condition of `Base <: Base` trivially succeeds. Assignment of `c = b` follows this proof subtree in a very similar way and goals are satisfiable.

#### Typechecking `method negTest01`
In `method negTest01` we are dealing with an assignment of a value of type `D` to a value of type `A`. That fails but it is interesting to see how typechecker reaches such conclusion. Right-click on the failed adaptation subgoal (`Can we adapt expression to the expected type 'Base'?`). That reveals two additional subtyping tests before announcing that types `D` and `A` are not compatible. Let's expand again up to `What is the least type instance...` goal which basically tries to get a base type of `D` wrt to `A`. Its subgoal shows that finding such a type failed (compiler attempts to perform a conformance test ` NoType <: Base`).
Where did `NoType` come from? It's typechecker way of saying that there is no such type which is the least instance of `Base` and at the same time a supertype of `D`.

Another tried subtyping check involves folding any constants in the expression (not shown) and using the type of the new expression in the subgoal to try to satisfy the subtyping test again. Since we had no constants involved in the expression `d`, type will not alter and the condition will fail in the same manner.
Method `negTest02` represents the same kind of problem and involves similar subtyping checks but we leave it to the user to explore it on their own.

#### Typechecking method `test02` and `negTest03`
So what kind of subtype checking is involved when we are dealing with simple function application? For that let's select `test02` and focus on `res*` definitions. When you are checking the body of value `res1` you are typechecking value application. For that we deal with three subgoals - `Typecheck function`, `Function in application context has been typechecked` and typechecking the actual application. `m1`'s' type (hover with `Ctrl`) is a Method type `(x: Base, y: B)Base` which is what you would expect from the signature of `m1`.

Later the compiler will typecheck the actual application itself and that involves typechecking each of the arguments against the formal parameter's type as an expected type (`Typecheck single argument`). At this point adaptations follow the standard pattern (remember to expand the advanced information explicitly) and you can see how subtyping tests succeed.

Similar kind of pattern exists for other value definitions.
Value definitions presented in `negTest03` show how adaptation stage for each of the arguments fail and what kind of subtype checking is necessary to reach those goals.

## More subtyping debugging ##

**Subtyping03.scala** experiments further with the interaction of variance and subtyping.

Class `Box` contains two, almost identical, methods `pack1` and `pack2`. The only difference between the two is the type of the parameter `xs`. The user should notice that the declaration of [List](http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.immutable.List) is covariant in its type parameter, whereas in the case of [Set](http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.immutable.Set) it is invariant. As we will show below this has serious consequences on how the two collections can be used.

#### Playing with `List` in `test01`
Method `test01` defines values `v1` and `v2` consisting of different kinds of `Food` and `Fruit`, respectively. Verification of the values' signatures shows in detail how the type is inferred. Since `List(Apple, Banana, Carrot)` is in reality a simple value application `List.apply(Apple, Banana, Carrot)` where function (value) is of type `(xs: A*)List[A]` with undetermined type parameter `A`, it becomes apparent why we need to infer type argument in order to satisfy the application goal. This involves typechecking each of the arguments in succession and then doing the actual inference in goal `Can we infer precise type arguments for method instance...?`. The latter involves subtyping checks between types of the arguments and corresponding types of the formal parameters. However as one can explore deeper, those subtype checks, similarly as before, do not really give a definitive answer for the satisfiability of the subtyping constraint but rather add additional constraints.

Those constraints are taken into account for the goal `Given results from conformance tests with expected type, can type variables be finally resolved?` where all the unknown type variables (here `A`) are solved and later substituted in the original types and/or expression trees. Hence the signatures of `v1` and `v2` are inferred to be `List[Food]` and `List[Fruit]`, respectively. 

Let's have a closer look at applications of `pack1` to the just typechecked values. In the first case we will fail in the adaptation stage, when typechecking `v1`. In order to understand better why `List[Food]` is not a subtype of `List[Fruit]` let's expand the hidden subtype checks at the adaptation stage (`Can we adapt expression to the expected type?`). There it becomes clear that in order for the constraint `List[Food] <: List[Fruit]` to succeed, `Food <: Fruit` as per rules of the type arguments in the covariant position.
Similar checks happen for all the other applications in the method, yet there the types are compatible (we leave it to the reader as an exercise).

#### Playing with `Set` in `test02`

Verification of signatures of the initial values in `test02` follow the same pattern as before, where `w1` and `w2` end up with correct *least upper bounded* types `Set[Food]` and `Set[Fruit]`. However this time applications of `pack*` methods to the values will result in more errors. If one analyzes adaptation goals that are on the path of the errors it becomes clear that in order for them to succeed their type arguments have to agree. Expanding for example subgoal of the adaptation stage (`Can we adapt expression to the expected type: Set[Food]?`) in `c.pack2[Any](w1)` one has to satisfy the subtyping constraint `Set[Food] <: Set[Any]`. But one of the subgoals mentions that the type argument in `Set` is in invariant position (`Are the two type arguments in invariant position comparable?`) hence one has to satisfy two goals `Any <: Food` and `Food <: Any`. The former will always be false.

#### Playing with `Set` and type inference in `test03`

Given the errors from the previous method it may become suprising that application of `pack*` methods to sets without any intermediate values leads to fewer errors.
Application `c.pack2[Fruit](Set(Apple, Banana, Carrot))` is an interesting example where local type inference is capable of resolving to a more precise type.
This is because the type of the formal parameter `xs` (of `pack2`) is fully inferred by now and sets the expected type of the application `Set(Apple, Banana, Carror)` to `Set[Fruit]`. 

This goal now becomes similar to what we analyzed in method `test01` except that the arguments in the `Set` *apply* are now typechecked with a *lenient target type*. Where did this type come from? You can discover it manually (below) or use one of the helpers on the adaptation goal.


The answer to that question is hidden as one of the subgoals of their parents. Let's expand hidden goals of `Function is of Method Type... Can we resolve type parameters before typechecking the actual application?`. Advanced subgoal `Can we infer lenient types for arguments based on the context...?` should now be visible. It reveals that there is a concrete instantiation for the type variable `A` thanks to the subtyping check `Set[A] <: Set[Fruit]` and `A <: Fruit` & `Fruit <: A` in consequence.
Hence each of `Set`'s arguments is typechecked with a precise `Fruit` target type and we get a type error.

Although we were not so lucky in the first case, inferring lenient type argument which serves as a context type for the arguments is profitable in further cases. For instance in application `c.pack2[Any](Set(Apple, Banana, Carrot))`, arguments will be typechecked with the lenient target type `Any`, hence resulting in the `Set[Any]` type for the whole `Set` creation argument and finish successfully in typechecking application.
