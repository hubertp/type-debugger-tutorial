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
 - What kind of information does `[T >: A]` give us about local type parameter `T`?
 - What type argument has been inferred for type parameter `T`?
 - What constraint(s) is/are used for inferring the type `T`?
 - Do we calculate *least upper bound* or *greatest lower bound* of those constraint(s)? Why?

<!-- -->

### Inference02 (source at *inference/Inference02.sala*) ###
Let's have some fun with Nothing and some of the limitations of type inference.

#### test01 ####
 - For `res1(2)` why does the mismatch occur between `Nothing` and `Int`?
 - Where is the type for `T` inferred: at assignment to `res1` or at application `res1(2)`?
 - What is the type of the expression `res1`?
 - While infering `T` for `res1` what are the constraints that inferencer uses?
 - Could the typechecker infer `Any` instead of `Nothing` in general in this situation?
 - `foo` is a function. Yet it is partially applied and assigned to a value. What does the typechecker do for that to be possible?

 - What is the type of `res2`?
 - What are the constraints on `T` while inferring the type of `fooVariation`?
 - Do we calculate *least upper bound* or *greatest lower bound* of those constraint(s)? Why?
 - State precisely why did the compiler infer `T` to be of type `Any` and `Nothing` this time.

#### test02 ####
 - What type is inferred for `X` and what for `T` in `bip`?
 - Why is the type for `X` not `Nothing` or `Any`?
 - What are the constraints while inferring type arguments for `bip` for `T` and `X`, respectively?
 - Why is `T` not inferred to be the same as `X` even though it has it in its bounds?
 - Does the order of declaration of type parameters (compare `bip` with `bop`) have any difference on inference? Why?

#### test03 ####
 - What is the type of `res6`?
 - What types are inferred for `X` and `T` in `bar1`?
 - What are the constraint(s) on `T` and do we calculate `least upper bound` or `greatest lower bound` of the constraint(s) for its inference? Why?
- Does the order of declaration of type parameters (compare `bar1` with `bar2`) have any difference on inference? Why?

### Inference03 (source at *inference/Inference03.sala*) ###
Type inference for higher-kinded types.

#### test01 ####
 - Is it possible to give type argument(s) to `foo` application, that would make the whole application correct? If yes, please state them.
 - What constraint(s) (and on which type parameters) does the typechecker collect while applying `foo` to `new Base1`?
 - Are there any other sources of constraints? If yes, name them.
 - What is the inferred type argument for `T` and does it agree with it defined bounds? If not, say why.
 - What are the bounds of the type parameter `U` and does it agree with it defined bounds? If not, say why.

#### test02 ####
 - Is it possible to give type argument(s) to `bar` application, that would make the whole application correct? If yes, please state them.
 - What constraint(s) (and on which type parameters) does the typechecker collect while applying `bar` to `new Base1`?
 - What are the inferred type arguments for `bar`?
 - If we drop `T0 <: 0` bound, can we still infer correct type arguments? Why?

#### test03 ####
 - What type arguments would you typically expect the compiler to infer in the call to `create`?
 - What are the real inferred type arguments for `Z` and `X`? Which one does not match the bounds of the type parameter and why?
 - What are the collected constraint(s) for `Z`?

#### test04 ####
We want to define a generic partitioning function over `Iterable` (e.g. `Seq`) 

##### partition01
 - What is the type of `takeWhile` as a member of `xs`? Why?
 - What is the type of `p1`? Why?

##### partition02
 - What is the type of `takeWhile` as a member of `xs`? Why?
 - What is the type of `p2`? Why?
 
 - In `res1a` application of `partition2`: what are the constraint(s) on type parameters `CC` and `T` and where do they come from?
 - In `res2a` application of `partition2`: does the expected type affect the inference of the type parameters? If yes, how?

##### partition03
`CanBuildFrom` trait defined in Scala [docs](http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom) allows to build collections in a reusable way. `CanBuildFrom[-From, -Elem, +To]` (with type paramers) defined a way to build a collection, from a collection of type `From` (e.g. `Seq[_]`) having elements of type `Elem` (e.g. `Float`) to a target collection `To` (e.g. `List[Float]`).
Notice that the only difference in signature between `partition1` and `partition3` is the implicit parameter.

In `res2a` application:
 - Whay is the type of `res2a`?
 - What are the inferred type arguments for `T` and `CC`?
 - What is the inferred implicit argument? Where there any other competing implicits? What affects the inference of type argument for `To`?

In `res2b` application:
 - Whay is the type of `res2b`?
 - What are the inferred type arguments for `T` and `CC`?
 - Does the expected type affect the type inference of type arguments in the application? If yes, how?
 - What is the inferred implicit argument? Where there any other competing implicits? 

### Inference04 (source at *inference/Inference04.sala*)###
<!-- existentials are getting inferred here -->
#### test01 ####
 - What is the expected type while typechecking (initially) the arguments of `foo`?
 - What is the type of the argument `new A()` and `new B[Int]()`? Why?
 - What is the type argument inferred for the application of `foo`?
 - What are the constraint(s) that the compiler will use to infer the type for `S`, what type will it infer?
 - Could the compiler infer `R[Nothing]` or `R[Any]` for `S`? Why?

### Inference05 (source at *inference/Inference05.sala*) ###
#### test01 ####
 - what is the type of `new A{}` and `new B{}`?
 - What is the inferred type for `a`? Why?

#### test02 ####
 
 - What is the type of the *then* branch in conditional for `b`?
 - What is the type of the *else* branch in conditional for `b`?
 - What is the type of `b`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

#### test03 ####
 - What is the type of `c`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

#### test04 ####
 - What is the type of `d`? Explain in terms of finding *least upper bound* and/or *greatest lower bound*.

 