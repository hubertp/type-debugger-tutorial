---
layout: page
title: "Questions: Type inference"
tagline: "Questions mostly focus on type inference"
---
{% include JB/setup %}

<!--- list of all examples -->

### TypeInference01 ###

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

### TypeInference02 ###
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

### TypeInference03 ###
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