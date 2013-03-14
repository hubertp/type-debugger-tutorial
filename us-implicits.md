---
layout: page
title: "Questions: Implicit search"
---
{% include JB/setup %}


### Implicits01 ###
 - While searching for the argument for implicit parameter `tc`, are each of the following implicits `firstImplicit`, `secondImplicit`, `thirdImplicit`, `fourthImplicit`, `fifthImplicit` and `sixthImplicit` in the implicit search scope?
 - Which implicit has been applied as an implicit argument for `tc`?
 - Is there more than one implicit that the compiler verified to be correct for that position? If so, why was the one you mentioned above selected and there was no ambiguity?


### Implicits02 ###
In this example we would like to use implicits to safely do matrix-matrix, matrix-vector etc. products. The test provides multiple combinations (not all of them making sense).

Your task is to say which of the implicits in `MultiplicationComp` are verified by the compiler to be correct as arguments for implicit parameter `c` in `multiplyMe` application. If there is more than one applicable, specify how does the typechecker select the most specific one or why it is not able to (on failure).

Questions:
 - Why cannot it resolve ambiguity in 2) but succeeded in 1)?
 - Name all the ambiguous implicits in 2)
 - Why cannot it resolve ambiguity in 4) but succeeded in 3)?
 - Name all the ambiguous implicits in 4)
 - Why cannot it find the implicit for 6) but succeeded in 5?

### Implicits03

#### test01
- What type is inferred for `T` in the first `universalComp` application?
- Is `aOrdering` in scope? If yes, why is it not picked by the implicit search?
- There will be other implicits that potentially apply as implicit arguments given their generic type but compiler will (correctly) fail verify them. Name them and explain why are they rejected (hint: this question does not ask about implicit imported from `scala.Predef`).
- How would you fix the type error?

#### test02
- How many implicits are verified by the compiler to be compatible with the search type for the implicit parameter?
- If there is more than one, name them and explain why there is no ambiguity.
- What is the implicit argument that the compiler applied to the application?

### Implicits04
Tests that follow experiment with chaining the implicits.

#### test01
 - Is there a direct implicit view of type `Int => SumTuple3` (in order for the selection `1.sum` to succeed)?
 - During the initial search for an implicit view, to satisfy member selection, typechecker will verify two potential possibilities. Name them.
 - What implicits (and in what sequence) are used in order to satisfy member selection `1.sum`?

 - Which implicit(s) are being verified by the compiler during the first implicit conversion of`(2, 5)`?
 - Which implicits (and in what sequence) are used in order to satisfy the typechecking of `(2, 5).sum`?

 - Why does the compiler complain about missing view `(Int, Int, Int) => (Int, Int)` in `(1, 2, 3).sum`?

### Implicits05
Implicit search can often return an error message related to diverging implicits. It is relatively easy to construct this problem yourself (as we will show) but it can also appear while using normal Scala collections.

#### test01
Consider application of `doWork`:
 - Which implicits arguments are considered as valid possibilities and will be verified as initial arguments for `process` parameter?
 - Which parameter of the `transitive` implicit diverges?
 - Are implicits `pre2Main` and `main2Final` are considered as valid possibilities and verified as arguments of `transitive`?
 - How would the diverging expansion in this particular case look like?

#### test02
<!-- TODO: give link -->
The signature of the `sorted` [method](http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.immutable.List) is `def sorted[B >: A](implicit ord: math.Ordering[B]): List[A]`.
 - Which implicit argument is applied as an argument to the `sorted` method of `listOfFoo`?
 - There is more than one implicit possible as an argument to the `sorted` method? Why does the compiler select the one you stated in your previous answer?

 - Which implicit arguments are taken into account when searching for implicit argument of the `sorted` method of `listOfBar`?
 - Can the typechecker use the same implicit argument as in the case of `listOfFoo`? Why?
 - What is the sequence of implicits that causes the divergence?
 - Give a possible fix for the underlying problem (so that you can call `sorted` method on `listOfBar`)?

#### test03
Consider a `SortedSet` trait available from the standard library: http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.SortedSet
 - What is the type of `set1`?
 - Does the application `++` present in `set1` involve finding implicits at any point of typechecking the body of the value?

 - What is the type of the member selection `SortedSet.empty` only (in `set2`)?
 - Give an example of a sequence of implicit arguments which would lead to the divergence?

### Implicits06 (source at *implicits/Implicits2.scala* and *implicits/package.scala*)
Notice that you are required to compile the package object file as well (it contains some implicits).

### Implicits06
Consider implicits `packageObjectFAction` (in *implicits/package.scala*), `basicImpl`, `generalImpl`, `otherGeneralImpl`, and `someImpl`.

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1.0)` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `identity(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `bar(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?