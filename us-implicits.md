---
layout: page
title: "Questions: Implicit search"
---
{% include JB/setup %}


### Implicits01 ###
 - While searching for the argument for implicit parameter `tc`, are each of the following implicits `firstImplicit`, `secondImplicit`, `thirdImplicit`, `fourthImplicit`, `fifthImplicit` and `sixthImplicit` in the implicit search scope?
 - Which implicit has been applied as an implicit argument for `tc`?
 - Is there more than one implicit that the compiler verified to be applicable for that position? If so, why was the one you mentioned above selected?


### Implicits02 ###
In this example we would like to use implicits to safely do matrix-matrix, matrix-vector etc. products. The test provides multiple combinations (not all of them making sense).

Your task is to say which of the implicits in `MultiplicationComp` are verified by the compiler to be correct as arguments for implicit parameter `c` in `multiplyMe` application. If there is more than one applicable, specify how does the typechecker select the most specific one or why it is not able to (on failure).

Questions:
 - Why cannot it resolve ambiguity in 2) but succeeded in 1)
 - Why cannot it resolve ambiguity in 4) but succeeded in 3)
 - Why cannot it find the implicit for 6) but succeeded in 5?

### Implicits03

#### test01
- What type is inferred for `T` in the first `universalComp` application?
- Is `aOrdering` in scope? If yes, why is it not picked by the implicit search?
- There will be other implicits that potentially apply as implicit arguments but compiler will (correctly) fail to typecheck them. Name them.
- How would you fix the type error?

#### test02
- How many implicits are verified by the compiler to be compatible with the search type for the implicit parameter?
- If there is more than one, name them and explain why there is no ambiguity.
- What is the implicit argument that the compiler applied to the application?

### Implicits04
Tests that follow experiment with chaining the implicits.

#### test01
 - Is there a direct implicit view of type `Int => SumTuple3` (in order for the selection `1.sum` to succeed)?
 - During the very first tried implicit conversion in the member selection, which implicit(s) are verified by the compiler for the qualifier `1` (so that it has a member `sum`)?
 - What implicits (and in what sequence) are used in order to satisfy it?

 - Which implicit(s) are being verified by the compiler during the first implicit conversion of`(2, 5)`?
 - Which implicits (and in what sequence) are used in order to satisfy the typechecking of `(2, 5).sum`?

 - Why does the compiler complain about missing view `(Int, Int, Int) => (Int, Int)` in `(1, 2, 3).sum`?

### Implicits05
Implicit search can often return an error message related to diverging implicits. It is relatively easy to construct this problem yourself (as we will show) but it can also appear while using normal Scala collections.

#### test01
Consider application of `doWork`:
 - Which implicits are being verified as initial arguments for `process` parameter?
 - Which parameter of the `transitive` implicit diverges?
 - Are implicits `pre2Main` and `main2Final` being verified as possible arguments of `transitive`?
 - How would the diverging expansion in this particular case look like?
 - What type arguments are inferred for `A`, `B` and `C` of `transitive` during its implicit search?

#### test02
<!-- TODO: give link -->
The signature of the `sorted` method is `def sorted[B >: A](implicit ord: math.Ordering[B]): List[A]`.
 - Which implicit argument is applied as an argument to the `sorted` method of `listOfFoo`?
 - There is more than one implicit possible as an argument to the `sorted` method? Why does the compiler select the one you stated in your previous answer?

 - Which implicit arguments are taken into account when searching for implicit argument of the `sorted` method of `listOfBar`?
 - Can the typechecker use the same implicit argument as in the case of `listOfFoo`?
 - What is the sequence of implicits that causes the divergence?
 - Give a possible fix for the underlying problem (so that you can call `sorted` method on `listOfBar`)?

#### test03
Consider a `SortedSet` trait available from the standard library: http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.SortedSet
 - What is the type of `set1`?
 - Does the application `++` present in `set1` involve applying implicit arguments at any point of typechecking the body of the value?

 - What is the type of the member selection in `SortedSet.empty`?
 - What is a sample sequence of implicits which would lead to the divergence?

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