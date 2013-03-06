---
layout: page
title: "Questions: Subtyping"
---
{% include JB/setup %}

### Subtyping01 (source at *subtyping/Subtyping01.scala*)
For each of the applications in `test* (arg*)` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all final subtyping checks between base types. e.g. `Map[A, B] <: Map[C, D]` it would be `A <: C`, `C <: A` and `B <: D`.

 Answers:

 - test1:
 - test2:
 - test3: 
 - test4:
 - test5:
 - test6:
 - test7:
 - test8:

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



### Subtyping03 (source at *subtyping/Subtyping03.scala*)

### test1 ###
 - How was the type argument `T` inferred in application `foo`?
 - How was the type argument `B` inferred in application `foo`?
 - Why does it not infer `T` to be equal to `B`?

### test3 ###
 - Why cannot the compiler infer the type argument for `T` to be of type `Integer`?
 - How does the expected type of `y` affect the inference of type arguments in this application?

### test4 ###
 - What constraints are taken into account while inferring type argument for `T` in application `foo(x, y)`?
 - What constraints are taken into account while inferring type argument for `S` in application `foo(x, y)`?
 - How did the compiler came up with the complicated type involving `Serializable` for `T`?
 - Why is the type argument for `S` not inferred as `Serializable` as well?
 - Why is the inferred type argument for `S` simply not `Any`?

### test5 ###
 - Why is the inferred type argumnet for `T` in the `foo(x, y)` application now different?
 - Why is the inferred type argument for `S` in the `foo(x, y)` application now different?
 - Why application of `foo` to `x` and `y` now typechecks but it didn't typecheck in the previous test?

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
 - Map is invariant in the first type parameter and covariant in the second one. How does this affect constraints that the compiler will use to infer type argument `T` in the application `foo(m1)`?
 - What is the inferred type argument for `T`? 
 - CovMap is covariant in both type parameters. How does this affect the constraints that the compiler will use to infer type argument `S` in the application `bar(m2)`?
 - What is the inferred type argument for `S`? 

 - Does the type of the keys (`Integer`) of the `m3` `Map` affect the inferred type argument `T` in the application?
 - Does the type of the keys (`Integer`) of the `m4` `Map` affect the inferred type argumnet `S` in the application?


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