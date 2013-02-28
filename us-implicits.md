---
layout: page
title: "Questions: Implicits"
tagline: "Questions related to implicit search"
---
{% include JB/setup %}

### Implicits01 ###
 - Why does the assignment `a` fail, whereas `b` succeed?
 - Does typechecking `a` consider the implicit `int2B` at all?
 - Are there any other implicits in the current scope that the compiler will check? If yes, where are they located?

### Implicits02 ###
 - What is the inferred type of `A` in `foo` application?
 - Are implicits `firstImplicit`, `secondImplicit` and `thirdImplicit` in the scope for the implicit argument?
 - Which implicit has been selected as an implicit argument for `tc`?
 - Is there more than one applicable implicit for that position? If so, why was the one above selected?

### Implicits03 ###
 - What is the inferred implicit argument for `foo`?
 - What is the *shape* of the type that will be searched for by the typechecker?
 - Is `firstImplicit` and `secondImplicit` in scope? If yes, then are they applicable for the searched parameter?
 - If there is more than applicable implicit how did the typechecker do the selection?

### Implicits4 ###
In this example we would like to use implicits to safely do matrix-matrix, matrix-vector etc. products. The test provides multiple combinations. Your task is to say which of the four implicits in `ImplicitsForComp` are applicable for the given application. If there is more than one applicable, specify how does the typechecker select the most specific one.
Examples:
1: 
2:
3:
4:
5:
6:
7:
8:
9:
10:
11:
12:

### Implicits5 (files: Implicits2.scala and package.scala)
Notice that in the next two examples you are required to compile the package object as well (it contains some implicits).

#### test01 ####
Consider implicits: `packageObjectAction`, `localImpl`, `longImpl` and `generalImpl`. 
- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Give a one sentence explanation in each case.
- Why is the compiler not able to resolve the ambiguity in the `foo(1)` case?

- For each of the implicits say whether they are applicable or not as implicit arguments for the application of `foo(1.0)` context. In one sentence explain why.
- If more than one implicit is verified to be acceptable, then how does the compiler select the right one?
- Which implicit argument is selected and why?

#### test02 ####
- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Give a one sentence explanation in each case.
- Which implicit argument is selected and why?

- For each of the implicits say whether they are applicable or not as implicit arguments for the application of `foo(1.0)` context. In one sentence explain why.
- If more than one implicit is verified to be acceptable, then how does the compiler select the right one?
- Which implicit argument is selected and why?

### Implicits6
Consider implicits `packageObjectGAction`, `basicImpl`, `generalImpl`, `lessGeneralImpl`, and `someImpl`.

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Give a one sentence explanation in each case.
- Which implicit argument is selected and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1.0)` context. Give a one sentence explanation in each case.
- Which implicit argument is selected and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `identity(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is selected and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `bar(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is selected and why?

### Implicits7
Consider a simple structure representing a `Result` of a computation that scales some `Size` either in `Horizonal` or `Vertical` direction. We want to know how the expected type of the formal parameter affects the adaptation of the qualifier (notice that `Size` has no `scale` member).

#### test01 
Only two cases:
- get1(s.scale(v))
- get3(s.scale(v))
succeed in typechecking.

For each of them answer the following questons:
- Which implicit(s) were applied?
- Was there more than one implicit view applicable at any point of typechecking the above statements? If yes, how did the typechecker select the more specific one?

#### test02
Typechecking first `onInvalid` application succeeds.
- What implicit conversions were used in order to typecheck the application?

The second application fails even though it is very similar.
- We have a `square` implicit in the scope which would intuitively transform the expression to the previous case. Why are implicit conversions from above no longer applicable in this case? 
<!--- todo: anything from the list below ? -->
<!--
    //Q: Did we really allow chaining of implicits here?
    //Q: Did you expect the following expression to typecheck?
    //Q: How would you apply implicits for the expression to succeed?
    //Q: Did implicit search fail to find the correct implicit?
    //Q: Which implicit is the culprit? 
-->
### Implicits8

#### test01
- What type is inferred for `T` in the first `universalComp` application?
- Is `aOrdering` in scope? If yes, why is it not picked by the implicit search?
- Are there any other implicits that the compiler will try to verify?
- How would you fix the type error?

#### test02
- How many implicits are verified by the compiler to see if they match the epected type?
- If more than one, why is there no ambiguity?
- What is implicit argument that the compiler found?
- Where is the implicit argument defined?

### Implicits9 (source at Implicits4.scala)
Inspired by the [Lightweight Module Staging](http://dl.acm.org/citation.cfm?id=1868314) this example shows a possible error in understanding by the user in method `plus`. 
 - Why is there no mismatch between Rep[T] and Def[T]?
 - How many implicit arguments do we need to search for in the body of `plus`?
 - Why do we search for an implicit of type Show[T]?
 - Is `genericShow` in scope and if yes why is it not picked?

### Implicits10
Examples will experiment with the concept of chaining implicits.

#### test01
 - Why does the application of `correct` to `b` succeed?
 - Why does the application of `correct` to `a` fail? Compare it with the scenario that would apply the views from the method explicitly.

#### test02
 - Is there a direct implicit view of type `Int => SumTuple3`?
 - Which implicit(s) are initially verified by the compiler to be applicable to the expression `1.sum`?
 - We determined in the previous question that mulitple implicit views are not applied (chainged) by the compiler. Yet, `1.sum` is typechecked correctly. What implicits (and in what sequence) are used in order to satisfy it?

 - Which implicit(s) are initially verified by the compiler to be applicable to the expression `(2, 5).sum`?
 - What implicits (and in what sequence) are used in order to satisfy the typechecking of `(2, 5).sum`?

 - Is there any implicit view initially verified to be applicable for the expression `(1,2,3).sum`?
 - Why does the compiler complain about missing view `(Int, Int, Int) => (Int, Int)`?

#### test03
 - Which implicit view(s) are used to typecheck?
 - In `test02` we saw that there is an implicit that transforms `1.sum`. Since `intToSym` is now in scope, why is it there no ambiguity?

### Implicits11
In order to *pimp* the `Seq[Char]`, typechecker has to find an implicit argument of type `<:<[A,B]` (defined in `scala.Predef`) which is a witness that `A` is a subtype of `B`.

- In `s.repeats('a')` what implicit argument is inferred for `isSeq`? 
- We have established in the value definition of `s` that `String` can be treated as a `Seq[Char]`. Why then the typechecker will not apply the same implicit argument as in the previous question?

- Similarly as before we can assign a value of type `Char` to `x` of type `Int`.
  Why it cannot find then an implicit argument that would prove that `Seq[Char] <:< Seq[Int]` i.e. that `Seq[Char]` is a subtype of `Seq[Int]`?

### Implicits12
We define a generic way of comparing `Pair`'s carrying values of the same type. 
As one can see with the case of assignment of `0` to `someInt`, value of type `Int` can be assigned to something of type `Ordered[Int]`. Therefore it may be interesting to answer the following questions:
 - What is the difference between the two calls of `a lessThan a` and `a lessThan2 a`?
 - Which implicit argument makes the latter application succeed? Where is it defined?

### Implicits13 (source at Implicits4.scala)
Implicit search can often return an error message related to divergin implicits. It is relatively easy to construct this problem yourself (as we will show) but it can also appear while using normal Scala collections.

#### test01
 - Which implicits in the scope are verified by the implicit search mechanism?
 - Which parameter of the `transitive` implicit diverges?
 - Are implicits `pre2Main` and `main2Final` being verified as possible arguments of `transitive`?
 - How would the diverging expansion look like?
 - What kind of type arguments are inferred for type parameters `A`, `B` and `C`?

#### test02
 - What is the signature of the method `sorted` as a member of the qualifier `listOfFoo`?
 - Which implicit argument is selected as an argument to the `sorted` method of `listOfFoo`?
 - What implicit(s) are being verified by the compiler as possible arguments to the `sorted` method? Why does the compiler select the one from the previous question?

 - Which implicits are taken into account when searching for implicit argument of the `sorted` method `of `listOfBar`?
 - Can the typechecker use the same argument as in the case of `listOfFoo`?
 - What is the sequence of implicits that causes the divergence?
 - Briefly give a possible fix for the underlying problem (so that you can call `sorted` method on `listOfBar`)?

#### test03
 - What is the type of `set1`?
 - Does the application `++` present in `set1` involve applying implicit arguments at any point of typechecking the body of the value?

 - What is the type of the member selection in `SortedSet.empty`?
 - What is a sample sequence of implicits which would lead to the divergence?

### Implicits14 (source at Implicits6.scala)
For the questions below we will be working with a `map` method. For `mutable.LinkedList` as well as `BitSet` it is first defined in [GenTraversable])http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.GenTraversable). It's full signature method is pretty hairy (see scaladoc for details): `def map[B, That](f: (A) => B)(implicit bf: CanBuildFrom[GenTraversable[A], B, That]): That` but typically you do not have to worry about it. Notice only the implicit parameter of type `CanBuildFrom` (if you are doing the tutorial step-by-step then we already discussed it in the [inference section](us-implicits.html).

`CanBuildFrom` trait defined in Scala [docs](http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom) allows to build collections in a reusable way. `CanBuildFrom[-From, -Elem, +To]` (with type paramers) defined a way to build a collection, from a collection of type `From` (e.g. `Seq[_]`) having elements of type `Elem` (e.g. `Float`) to a target collection `To` (e.g. `List[Float]`).

This will be important to answer the questions below.

- Whay is the type of `a`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `b`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `c`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `d`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 