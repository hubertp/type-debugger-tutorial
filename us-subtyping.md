---
layout: page
title: "Questions: Subtyping"
---
{% include JB/setup %}

### Subtyping01 (source at *subtyping/Subtyping01.scala*)
Each of the following applications is reported as an error by typechecker. Please state the final subtyping relation between types which could not be satisfied e.g. `Map[String, Number] <: Map[Int, Number]` it would be `String <: Int`.

 Answers:

 - test2:
 - test6:
 - test7:
 - test8:

### Subtyping02

### test02 ###
 - What is the inferred type argument for `B`? Why?
 - Does the type annotation of `y` affect the inference of type arguments in this application?

### test03 ###
 - Why is the inferred type argument for `T` `Serializable`? Explain in terms of solving the constraint collected in order to infer the type argument.
 - Why is the type argument for `S` not inferred as `Serializable` as well? Explain in terms of solving the constraint collected in order to infer the type argument.

### test04 ###
 Map is invariant in the first type parameter and covariant in the second one. CovMap is covariant in both type parameters. 

 - What type constraints affect the inference of type argument for `T` in the application `foo(m1)`?
 - What is the inferred type argument for `T` and why? (explain using your answer to the previous question)?
 - What type constraints affect the inference of type argument for `S` in the application `bar(m2)`?
 - What is the inferred type argument for `S` and why? (explain using your answer to the previous question)?

 - Does the type of the keys (`Integer`) of `m3`'s `Map` affect the inferred type argument `T` in the application? Why?
 - Does the type of the keys (`Integer`) of `m4`'s `Map` affect the inferred type argument `S` in the application? Why?