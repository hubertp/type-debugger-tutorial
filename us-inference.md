---
layout: page
title: "Questions: Type inference"
---
{% include JB/setup %}

<!--- list of all examples -->

### Inference01 (source at *inference/Inference01.sala*) ###

#### test01 ####
 - What is the return type of `test01`
 - How is the return type inferred?

#### test02 ####
 There is a type mismatch between `Base` and `Product with Serializable with Base`.

 - Where does the expected type come from?
 - How was the expected type inferred?

#### test03 ####
 - What kind of information does `[T >: A <: AnyRef]` give us about local type parameter `T`?
 - What constraint(s) is/are used for inferring type argument `T`?
 - Do we calculate *least upper bound* or *greatest lower bound* of those constraint(s)? Why?
 - What type argument has been inferred for `T`?

<!-- - -->

### Inference02 (source at *inference/Inference02.sala*) ###
Let's have some fun with Nothing and some of the limitations of type inference.

#### test01 ####

 - Where is the type argument for `T` inferred: at assignment to `res1` or at application `res1(2)`?
 - What is the type of the expression `res1`?
 - While infering `T` in `foo` what are the constraints on the type parameter that inferencer uses?
 - Why doe the typechecker not infer `Any` instead of `Nothing` in general for `T` in this situation?
 - `foo` is a function. Yet it is partially applied and assigned to a value. What does the typechecker do for that to be possible?

 - What is the type of `res2`?
 - What are the constraints on `T` while inferring the type of `fooVariation`?
 - Do we calculate *least upper bound* or *greatest lower bound* of those constraint(s)? Why?
 - Why is the inferred type argument different than in the previous case ?

#### test02 ####
 - What type argument is inferred for `X` and what for `T` in `bip`?
 - What are the constraints while inferring type argument of `X` for `bip`?
 - Why is the inferred type argument for `X` not a) `Nothing` b) `Any`?
 - Why is `T` not inferred to be the same as `X`, especially because it has it in its bounds?
 - Does the order of declaration of type parameters (compare `bip` with `bop`) have any difference on inference? Why?

#### test03 ####
 - What type argument is inferred for `X` in the `res6` application?
 - What are the constraint(s) on type parameter `T` and do we calculate `least upper bound` or `greatest lower bound` of the constraint(s) for its inference? Why?

### Inference03 (source at *inference/Inference03.sala*) ###
Type inference for higher-kinded types.

#### test01 ####
 - Is it possible to give type arguments to `foo` application, that would make the whole application correct? If yes, please state them.
 - What constraint(s) will the typechecker collect on type parameter `T` while applying `foo` to `new Base`?
 - What constraint(s) will the typechecker collect on type parameter `U` while applying `foo` to `new Base`?
 - What is the inferred type argument for `T` and does it agree with it defined bounds? If not, say why.
 - What is the inferred type argument for `U` and does it agree with it defined bounds? If not, say why.

#### test02 ####
 - Is it possible to give type arguments to `bar` application, that would make the whole application correct? If yes, please state them.
 - What constraint(s) will the typechecker collect on type parameter `T` while applying `bar` to `new Base`?
 - What constraint(s) will the typechecker collect on type constructor `U` while applying `bar` to `new Base`?
 - Why is the compiler able to infer acceptable type arguments but failed in *test01*?

<!--- - If we drop `T0 <: A` bound, can we still infer correct type arguments? Why? -->


<!---
#### test03 ####
 - What type arguments would you typically *expect* the compiler to infer in the call to `create` that would make it typecheck?
 - What are the real inferred type arguments for type parameters `Z` and `X`?


 Which one does not match the bounds of the type parameter and why?
 - What are the collected constraint(s) for `Z`?
-->

#### test04 ####
We want to define a generic partitioning function over `Iterable` (e.g. `Seq`) that takes a collection and a function which determines the split point of the collection. 

##### partition01
 - What is the type of `takeWhile` as a member of `xs`? Why?
 - What is the type of `p1`? Why?

<!---
##### partition02
 - What is the type of `takeWhile` as a member of `xs`? Why?
 - What is the type of `p2`? Why?
 
 - In `res1a` application of `partition2`: what are the constraint(s) on type parameters `CC` and `T` and where do they come from?
 - In `res2a` application of `partition2`: does the expected type affect the inference of the type parameters? If yes, how?
-->

##### partition03
`CanBuildFrom` trait defined in Scala [docs](http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom) allows to build collections in a generic way and it will allow us to build the desired partitioning function. `CanBuildFrom[-From, -Elem, +To]` (here with type paramers) defines a way to build a collection, from a collection of type `From` (e.g. `Seq[_]`) having elements of type `Elem` (e.g. `Float`) to a target collection `To` (e.g. `List[Float]`).
Notice that the only difference in signature between `partition1` and `partition3` is the implicit parameter.

 - What is the type of `res2a`?
 - What are the inferred type arguments for `T` and `CC`?
 - We do not provide the implicit argument for `cbf` directly therefore the compiler will have to infer it. Compiler will search for an implicit argument of what type?
 - What is the inferred implicit argument for parameter `cbf`?
 - Were there any other competing implicits in the application of `partition3`? If yes, name one.
 - What affects the inference of type argument for `To` in `CanBuildFrom` parameter?

<!---
In `res2b` application:
 - Whay is the type of `res2b`?
 - What are the inferred type arguments for `T` and `CC`?
 - Does the expected type affect the type inference of type arguments in the application? If yes, how?
 - What is the inferred implicit argument? Where there any other competing implicits? 
-->

<!--- SKIP QUESTION 
### Inference04 (source at *inference/Inference04.sala*)###
#### test01 ####
 - What is the expected type while typechecking (initially) the arguments of `foo`?
 - What is the type of the argument `new A()` and `new B[Int]()`? Why?
 - What is the type argument inferred for the application of `foo`?
 - What are the constraint(s) that the compiler will use to infer the type for `S`, what type will it infer?
 - Could the compiler infer `R[Nothing]` or `R[Any]` for `S`? Why?
-->
### Inference05 (source at *inference/Inference05.sala*) ###


#### test01 ####
 
 - What is the type of the *then* branch in conditional for `b`?
 - What is the type of the *else* branch in conditional for `b`?
 - What is the type of `b`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

#### test02 ####
 - what is the type of `new A{}` and `new B{}`?
 - What is the inferred type for `a`? Why?

#### test03 ####
 - What is the type of `c`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

#### test04 ####
 - What is the type of `d`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.
