---
layout: page
title: "Questions: Type inference"
---
{% include JB/setup %}

<!--- list of all examples -->

### Inference01 (source at *inference/Inference01.sala*) ###

#### test02 ####
 Compiler reports a type mismatch between `Base` and `Product with Serializable with Base`.

 - Where does the expected type come from?
 - How was the expected type inferred?

#### test04 ####
 
 - What is the type of `a`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

#### test05 ####
 - What is the type of `b`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

<!-- - -->

### Inference02 (source at *inference/Inference02.sala*) ###
Let's have some fun with Nothing and some of the limitations of type inference.

#### test01 ####

 - Where is the type argument `T` for `foo` inferred: 
  a) at assignment to `res1`
  b) at application `res1(2)`?
 - What is the type of the expression `res1`?
 - Why does the typechecker infer `Nothing` as type argument for `T`?
 - Why does the typechecker not infer `Any` instead of `Nothing` as type argument for `T` in this situation?
 - `foo` is a function. Yet it is partially applied and assigned to a value. What does the typechecker do for that to be possible?

 - What is the type of `res2`?
 - What are the constraints on `T` while inferring the type of `bar`?
 - Why is the inferred type argument for `T` now different than in the case of `foo` ?



<!-- todo still to decide -->
#### test02 ####
Compare almost identical definitions of functions `foo` and `bar`. Their usage (in some theoretical scenario) would be identical yet former reports a type error.
 - Is it possible to give type arguments to `foo` application, that would make the whole application correct? If yes, please state them.
 - What constraint(s) will the typechecker use in order to infer type argument `T` while  applying `foo` to `new Base`?
 - What constraint(s) will the typechecker use in order to infer type argument `U` while applying `foo` to `new Base`?
 - Why is the inferred type argument for `T` `Nothing`?

 - What constraint(s) will the typechecker use in order to infer type argument `T` while applying `bar` to `new Base`?
 - What constraint(s) will the typechecker use in order to infer type constructor `U` while applying `bar` to `new Base`?
 - Why is the compiler able to infer acceptable type arguments but failed in *test01*?

<!---
#### test02 ####
 - Array has no members `toList` and `toSet`. Which implicit views are found so that the expression typecheck for:
 a) `a.toList`
 b) `a.toSet`
 - Why does the **A** expression not require the type of the parameter but **B** does?
-->
<!---
#### test03 ####
 - What type arguments would you typically *expect* the compiler to infer in the call to `create` that would make it typecheck?
 - What are the real inferred type arguments for type parameters `Z` and `X`?


 Which one does not match the bounds of the type parameter and why?
 - What are the collected constraint(s) for `Z`?
-->

#### test03 ####
We want to define a generic partitioning function over `Iterable` (e.g. `Seq`) that takes a collection and a function which determines the split point of the collection. 

##### partition01
 - Compiler reports a type mismatch for the last tuple containing `p1` and `p2`. Why is the type of `p1` inferred to be `Iterable[T]` ant not `CC[T]`?


##### partition02
`CanBuildFrom` trait defined in Scala [docs](http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom) allows to build collections in a generic way and it will allow us to build the desired partitioning function. `CanBuildFrom[-From, -Elem, +To]` (here with type parameters and their variances) defines a way to build a collection, from a collection of type `From` (e.g. `Iterable[_]`) having elements of type `Elem` (e.g. `Float`) to a target collection `To` (e.g. `List[Float]`).
Notice that the only difference in signature between `partition1` and `partition3` is the implicit parameter.

 - What is the type of `res2a`?
 - What are the inferred type arguments for `T` and `CC`?
 - We do not provide the implicit argument for `cbf` directly therefore the compiler will have to infer it. Compiler will search for an implicit argument of what type?
 - What is the inferred implicit argument for parameter `cbf`?
 - Were there any other implicits which would be also suitable as implicit arguments in the application of `partition3`? If yes, name one.
 - How is the type argument for `To` in `CanBuildFrom` parameter inferred?


#### test04
 For the questions below we will be working with a `map` method. For `mutable.LinkedList` as well as `BitSet` it is initially defined in [GenTraversable](http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.GenTraversable). It's full signature method is pretty hairy (see scaladoc for details): `def map[B, That](f: (A) => B)(implicit bf: CanBuildFrom[GenTraversable[A], B, That]): That` but typically you do not have to worry about it. Notice only the implicit parameter of type `CanBuildFrom` as discussed in the question before.

 - What is the type of `a`? What implicit argument has been provided by the compiler? If the final type differs from the original collection one explain why. 

 - What is the type of `b`? What implicit argument has been provided by the compiler? If the final type differs from the original collection one explain why. 

