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

<!---
### Subtyping02 (source at *subtyping/Subtyping02.scala*)
For each of the applications of `m*` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all the final subtyping tests. e.g. For `Map[A, B] <: Map[C, D]` that would be `A <: C`, `C <: A` and `B <: D`.

Answers: 

 - m1:
 - m1:
 - m2:
 - m3:
 - m4:
 - m4:
 - m5:
-->


### Subtyping03 (source at *subtyping/Subtyping03.scala*)

### test1 ###
 - What is the inferred type argument for `B`? Why?
- Does the type annotation of `y` affect the inference of type arguments in this application?

### test3 ###
 - What is the inferred type argument for `B`? Why?
 - Does the type of `y` affect the inference of type arguments in this application?

### test4 ###
 - What constraints on `T` are taken into account while inferring type argument in application `foo(x, y)`?
 - What constraints on `S` are taken into account while inferring type argument in application `foo(x, y)`?
 - Why is the inferred type argument for `T` `Serializable`?
 - Why is the type argument for `S` not inferred as `Serializable` as well?
 - Why is the inferred type argument for `S` not `Any`?

<!---
### test5 ###
 - Why is the inferred type argumnet for `T` in the `foo(x, y)` application now different?
 - Why is the inferred type argument for `S` in the `foo(x, y)` application now different?
 - Why application of `foo` to `x` and `y` now typechecks but it didn't typecheck in the previous test?
-->
<!---
### test6 ###
 - What are the constraints (and their sources) on the type of `T`?
 - What is the inferred type of `T` for `foo` application? 
 - Do inferred types in the place of the wildcards of `foo` (`_ >: T`) represent the same type?
 - What are the inferred types of `T` and `S` for `bar` application?
 - Are the inferred types for the wildcards (in `foo`) and `S` (in `bar`) the same? Why?
-->

<!--- todo, a bit more tracking regarding existentials -->
### test7 ###
 Map is invariant in the first type parameter and covariant in the second one. CovMap is covariant in both type parameters. 

 - What type constraints affect the inference of type argument for `T` in the application `foo(m1)`?
 - What is the inferred type argument for `T` and why? (explain using your answer to the previous question)?
 - What type constraints affect the inference of type argument for `S` in the application `bar(m2)`?
 - What is the inferred type argument for `S` and why? (explain using your answer to the previous question)?

 - Does the type of the keys (`Integer`) of `m3`'s `Map` affect the inferred type argument `T` in the application? Why?
 - Does the type of the keys (`Integer`) of `m4`'s `Map` affect the inferred type argument `S` in the application? Why?


<!---
  needs better error location 
### Subtyping04 (source at *subtypingSubtyping04.scala*)
Let's assume a fragment of generic Tree data structure, as presented in the example. It looks similar to how Scala collections are built - you could easily extend Tree to mutable and immutable data structures. However the details are not so important.
Compiler will report the following error:

    userstudies/src/main/scala/examples/inference/Subtyping04.scala:21: error: illegal inheritance;
     self-type mutable.ITree[N] does not conform to TreeLike[N,mutable.ITree]'s selftype TreeLike[N,mutable.ITree]
                   with TreeLike[N, ITree] {
                        ^
    one error found

- What is the underlying problem?
- How would you fix this error?
-->