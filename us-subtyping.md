---
layout: page
title: "Questions: Subtyping"
---
{% include JB/setup %}

### Subtyping01 (source at *subtyping/Subtyping01.scala*)
For the purpose of this exercise remember that `Integer <: Number`.

Each of the following applications is reported as an error by typechecker. Please state the final subtyping relation between the types which could not be satisfied e.g. `Map[String, Number] <: Map[Int, Number]` it would be `String <: Int`. Report only the first one if there is more than one.

 Answers:

 - test1:
 - test2:
 - test3:
 - test4:

### Subtyping02

### test02 ###
 - Does the type annotation of `y` affect the **inference** of type arguments in this application?
 - What is the inferred type argument for `B` in application `foo(x)`?
   Type inferencer will infer the type by calculating the least upper bound of the constraints on the bounds. What are the constraints on the bounds of `B`? Why least upper bound and not greatest lower bound?
 
### test03 ###
 - Why did the typechecker infer `Serializable` as type argument for `T`? Explain in terms of solving constraints on the type's bounds collected in order to infer the type argument.
 - Why is the type argument for `S` not inferred as `Serializable` as well? Explain in terms of solving the constraints on the type's bounds collected in order to infer the type argument.

### test04 ###
 Map is invariant in the first type parameter (representing keys) and covariant in the second one (representing values). CovMap is covariant in both type parameters. 

 - What constraints on type bounds affect the inference of type argument for `T` in the application `foo(m1)`?
 - What is the inferred type argument for `T` and why? (explain using your answer to the previous question)?
 - What constraints on type bounds affect the inference of type argument for `S` in the application `bar(m2)`?
 - What is the inferred type argument for `S` and why? (explain using your answer to the previous question)?

 - Does the type of the keys (`Integer`) of `m3`'s `Map` affect the inferred type argument `T` in the application? Why?
 - Does the type of the keys (`Integer`) of `m4`'s `Map` affect the inferred type argument `S` in the application? Why?