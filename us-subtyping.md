---
layout: page
title: "Questions: Subtyping"
---
{% include JB/setup %}

### Subtyping01 (source at *subtyping/Subtyping01.scala*)
For this exercise we ask you **not** to use the Scala compiler to verify the answers.

For each of the applications in `test* (arg*)` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all the final subtyping tests. e.g. `Map[A, B] <: Map[C, D]` it would be `A <: C`, `C <: A` and `B <: D`.

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
For this exercise we ask you **not** to use the Scala compiler to verify the answers.

For each of the applications of `m*` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all the final subtyping tests. e.g. For `Map[A, B] <: Map[C, D]` that would be `A <: C`, `C <: A` and `B <: D`.

 - m1:
 - m1:
 - m2:
 - m3:
 - m4:
 - m4:
 - m5:

### Subtyping03 (source at *subtyping/Subtyping03.scala*)
Let's assume a fragment of generic Tree data structure, as presented in the example. It looks similar to how Scala collections are built - you could easily extend Tree to mutable and immutable data structures. However the details are not so important.
Compiler will report the following error:

    userstudies/src/main/scala/examples/inference/Subtyping04.scala:21: error: illegal inheritance;
     self-type mutable.ITree[N] does not conform to TreeLike[N,mutable.ITree]'s selftype TreeLike[N,mutable.ITree]
                   with TreeLike[N, ITree] {
                        ^
    one error found

- What is the underlying problem?
- How would you fix this error?

### Subtyping04 (source at *subtyping/Subtyping04.scala*)
For these questions remember that Integer is a subtype of Number.

### test1, test2, test3 ###
For each of the tests explain how were the types inferred (`T` and `B`) and whether the compiler could do a better job of inferring a more precise type (in other words could explicit type annotation fix it).

### test4 ###
 - What constraints are taken into account while inferring type arguments for `T` and `S` in application of `foo`?
 - How did the compiler came up with the horribly complicated type involving `Serializable` (for `T` and `S`)?
 - Is `Integer` a subtype of `Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: java.io.Serializable] with java.io.Serializable] with java.io.Serializable] with java.io.Serializable` ?
 - Is `String` a subtype of `Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: java.io.Serializable] with java.io.Serializable] with java.io.Serializable] with java.io.Serializable` ?

### test5 ###
 - What is the inferred type of `T` and `S`?
 - Why application of `foo` now typechecks but it didn't typecheck in the previous tests?

### test6 ###
 - Is there any expected type (type template) while typechecking `m` argument in both applications? If yes, why?
 - What is the inferred type of `T` for `foo` application? What are the lower and/or upper bound constraints (and their sources) used for its inference?
 - Do inferred types in the place of the wildcards of `foo` (`_ >: T`) represent the same type?
 - What are the inferred types of `T` and `S` for `bar` application?
 - Are the inferred types for the wildcards (in `foo`) and `S` (in `bar`) the same? Why?

### test7 ###
 - Is there any expected type (type template) while typechecking `m` argument in both applications? If yes, why?

 - What is the inferred type of `T` for `foo` application? Whare are the lower and upper bound constraints (and their sources) used for its inference?
 - What are the inferred types of `T` and `S` for `bar` application? Are they same as for `foo`? Explain.
 - Why does the application of `bar` succeed in this example but fail in the previous test?

