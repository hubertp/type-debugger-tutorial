---
layout: page
title: "Questions: Subtyping"
tagline: "Questions related to subtyping (and related)"
---
{% include JB/setup %}

### Subtyping01
For this exercise we ask you **not** to use the Scala compiler to verify the answers.
For each of the applications in `test* (arg*)` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all the final subtyping tests. e.g. For `Map[A, B] <: Map[C, D]` that would be `A <: C`, `C <: A` and `B <: D`.

 test1:
 test2:
 test3: 
 test4:
 test5:
 test6:
 test7:
 test8:

### Subtyping02
For this exercise we ask you **not** to use the Scala compiler to verify the answers.
For each of the applications in `m*` please answer the following questions:
 - Is the application type correct?
 - If it is not, please state the final subtyping relation that could not be satisfied.
 - If it is, please state all the final subtyping tests. e.g. For `Map[A, B] <: Map[C, D]` that would be `A <: C`, `C <: A` and `B <: D`.

 m1:
 m1:
 m2:
 m3:
 m4:
 m4:
 m5:

### Subtyping03


### Subtyping04
Let's assume a fragment of generic Tree data structure, as presented in the example. The details are not important. It looks similar to how Scala collections are built - you could easily extend Tree to mutable and immutable data structures. But we have an error

    userstudies/src/main/scala/examples/inference/Subtyping04.scala:21: error: illegal inheritance;
     self-type mutable.ITree[N] does not conform to TreeLike[N,mutable.ITree]'s selftype TreeLike[N,mutable.ITree]
                   with TreeLike[N, ITree] {
                        ^
    one error found

- What is the underlying problem?
- How would you fix this error?

### Subtyping05
For these questions remember that Integer is a subtype of Number.

### test1, test2, test3 ###
For each of the tests explain how were the type arguments inferred and whether the compiler could do a better job of inferring a more precise type (in other words could explicit type annotation fix it).

### test4 ###
 - What constraints are taken into account while inferring type arguments for `T` and `S`?
 - How did the compiler came up with the horribly complicated type involving `Serializable` (for `T` and `S`)?
 - Is `Integer` a subtype of `Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: java.io.Serializable] with java.io.Serializable] with java.io.Serializable] with java.io.Serializable` ?
 - Is `String` a subtype of `Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: Comparable[_ >: String with Integer <: java.io.Serializable] with java.io.Serializable] with java.io.Serializable] with java.io.Serializable` ?

### test6 ###
 - What is the inferred type of `T` and `S`?
 - Why does it typecheck but didn't typecheck before?

### test9 ###
 - Is there any expected type (type template) while typechecking `m` argument in both applications? If yes, why?
 - What is the inferred type of `T` for `foo` application? What are the constraints (and their sources) used for its inference?
 - Do inferred types for the wildcards of `foo` (`_ >: T`) represent the same type?
 - What re the inferred types of `T` and `S` for `bar` application?
 - Are the inferred types for the wildards and `S` the same? Why?

### test10 ###
 - Is there any expected type (type template) while typechecking `m` argument in both applications? If yes, why?

 - What is the inferred type of `T` for `foo` application? Whare are the constraints (and their sources) used for its inference?
 - What are the inferred types of `T` and `S` for `bar` application? Are they same as for `foo`? Explain your answer.
 - Why does the application of `bar` succeed in this example but fail in the previous test?

