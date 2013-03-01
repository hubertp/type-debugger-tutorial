---
layout: page
title: "Questions: Implicit search"
---
{% include JB/setup %}

### Implicits01 (source at *implicits/Implicits1.scala*) ###
 - Why does the assignment `a` fail, whereas `b` succeed?
 - Is implicit `int2B` in the implicit search scope while typecheckign `a` ?
 - Are there any other implicits in the scope of `a` that the compiler will check? If yes, where are they located?

### Implicits02 ###
 - What is the inferred type of `A` in `foo` application?
 - While searching for the argument for implicit parameter `tc`, are each of the following implicits `firstImplicit`, `secondImplicit` and `thirdImplicit` in the implicit search scope?
 - Which implicit has been applied as an implicit argument for `tc`?
 - Is there more than one implicit that the compiler verified to be applicable for that position? If so, why was the one above selected?

### Implicits03 ###
 - What is the inferred implicit argument for `foo(1)` application?
 - What is the *shape* of the type that will be searched for by the typechecker?
 - Is `firstImplicit` and `secondImplicit` in scope? If yes, then are they applicable for the searched parameter?
 - If there is more than one applicable implicit how did the typechecker do the selection?

### Implicits04 ###
In this example we would like to use implicits to safely do matrix-matrix, matrix-vector etc. products. The test provides multiple combinations.

Your task is to say which of the implicits in `MultiplicationComp` are verified by the compiler to be applicable as arguments for implicit parameter `c` in `multiplyMe` applcation. If there is more than one applicable, specify how does the typechecker select the most specific one or why it is not able to (on failure).

Examples:
 - 1: 
 - 2:
 - 3:
 - 4:
 - 5:
 - 6:
 - 7:
 - 8:
 - 9:
 - 10:
 - 11:
 - 12:



### Implicits05 (source at *implicits/Implicits2.scala* and *implicits/package.scala*)
Notice that in the next two examples you are required to compile the package object as well (it contains some implicits).

#### test01 ####
Consider implicits: `packageObjectAction` (in *implicits/package.scala*), `localImpl`, `longImpl` and `generalImpl`. 

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Provide very brief explanation for each.
- Why is the compiler not able to resolve the ambiguity in the `foo(1)` case?

- For each of the implicits say whether they are applicable or not as implicit arguments for the application of `foo(1.0)` context. Provide very brief explanation for each.
- Which implicit argument is applied and why?
- If more than one implicit is verified to be acceptable, then how does the compiler select the right one?

#### test02 ####
- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Provide very brief explanation for each.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not as implicit arguments for the application of `foo(1.0)` context. Provide very brief explanation for each.
- If more than one implicit is verified to be acceptable, then how does the compiler select the right one?
- Which implicit argument is applied and why?

### Implicits06
Consider implicits `packageObjectGAction` (in *implicits/package.scala*), `basicImpl`, `generalImpl`, `lessGeneralImpl`, and `someImpl`.

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1)` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `foo(1.0)` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `identity(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

- For each of the implicits say whether they are applicable or not, as implicit arguments for the application of `bar(foo(1))` context. Give a one sentence explanation in each case.
- Which implicit argument is applied and why?

### Implicits07 (source at *implicits/Implicits3.scala*))
Consider a simple structure representing a `Result` of a computation that scales some `Size` either in `Horizonal` or `Vertical` direction. We want to know how the expected type of the formal parameter affects the adaptation of the qualifier (notice that `Size` has no `scale` member).

#### test01 
In the following test, only two cases typecheck correctly:
- `get1(s.scale(v))`
- `get3(s.scale(v))`

For each of the above answer the following questons:
- Which implicit view(s) were applied and how?
- If there was more than one implicit view verified by the typechecker to be applicable for the conversion, then give their names and explain how did the typechecker select it.

#### test02
Typechecking first `onInvalid` application succeeds.
 - What implicit conversions were used in order to typecheck the application?

The second application of `onInvalid` fails even though it is very similar.
 - There is a `square` implicit in the scope which would intuitively transform the expression to the previous case. Why are the same implicit conversions no longer applicable in this case? 

### Implicits08

#### test01
- What type is inferred for `T` in the first `universalComp` application?
- Is `aOrdering` in scope? If yes, why is it not picked by the implicit search?
- Are there any other implicits that the compiler will try to verify? Name them.
- How would you fix the type error?

#### test02
- How many implicits are verified by the compiler to see if they match the epected type?
- If more than one, why is there no ambiguity?
- What is the implicit argument that the compiler applied to the application?
- Where is that implicit argument defined?

### Implicits09 (source at *implicits/Implicits4.scala*)
Inspired by the [Lightweight Module Staging](http://dl.acm.org/citation.cfm?id=1868314), this example gives a single type error when dealing with polymorphic types (here error occurs for method `plus`:
 - Why is there no mismatch between Rep\[T\] and Def\[T\]?
 - How many implicit arguments do we need to search for in the body of `plus`?
 - Why do we search for an implicit of type Show\[T\]?
 - Is `genericShow` in scope and if yes why is it not picked?

### Implicits10
Tests in the following section will experiment with chaining the implicits.

#### test01
 - Why does the application of `correct` to `b` succeed?
 - Why does the application of `correct` to `a` fail? What if you applied the functions explicitly?

#### test02
 - Is there a direct implicit view of type `Int => SumTuple3` (in order for the selection `1.sum` to succeed)?
 - During the first tried implicit conversion, which implicit(s) a verified by the compiler for the qualifier `1`?
 - We determined in `test01` that mulitple implicit views are not applied  by the compiler. Yet, `1.sum` is typechecked correctly. What implicits (and in what sequence) are used in order to satisfy it?

 - Which implicit(s) are being verified by the compiler during the first implicit conversion of`(2, 5)`?
 - Which implicits (and in what sequence) are used in order to satisfy the typechecking of `(2, 5).sum`?

 - Is there any implicit view initially verified to be applicable for the expression `(1,2,3).sum` (and which later fails?
 - Why does the compiler complain about missing view `(Int, Int, Int) => (Int, Int)`?

#### test03
 - Which implicit view(s) are used for typecheck `1.sum`?
 - In `test02` we saw that there is an implicit that transforms `1.sum`. Since `intToSym` is now in scope, why is it there no ambiguity?

### Implicits11
In this example we will attempt to *pimp* `Seq[Char]` to `RichSeq` using method `seq2RichSeq`. Typechecker has to find an implicit argument of type `<:<[A,B]` (defined in `scala.Predef`) which is a witness that `A` is a subtype of `B`.

- In `s.repeats('a')` what implicit argument is inferred for `isSeq` parameter of the implicit? 
- We have established in the value definition of `s` that `String` can be treated as a `Seq[Char]`. Why then the typechecker will not apply the same implicit argument as in the previous question in `"abc".repeats('a')`?

- Similarly as before we can assign a value of type `Char` to `x` of type `Int`. Why it cannot find then an implicit argument that would prove that `Seq[Char] <:< Seq[Int]` i.e. that `Seq[Char]` is a subtype of `Seq[Int]`?

### Implicits12
We define a generic way of comparing `Pair`'s carrying values of the same type. 
As one can see with the case of assignment of `0` to `someInt`, value of type `Int` can be assigned to something of type `Ordered[Int]`. Therefore it is interesting to answer the following questions:
 - What is the difference between the two calls of `a lessThan a` and `a lessThan2 a`?
 - Which implicit argument makes the latter application succeed? Where is it defined?


### Implicits13 (source at *implicits/Implicits4.scala*)
Implicit search can often return an error message related to diverging implicits. It is relatively easy to construct this problem yourself (as we will show) but it can also appear while using normal Scala collections.

#### test01
Consider application of `doWork`:
 - Which implicits are being verified as initial arguments for `process` parameter?
 - Which parameter of the `transitive` implicit diverges?
 - Are implicits `pre2Main` and `main2Final` being verified as possible arguments of `transitive`?
 - How would the diverging expansion in this particular case look like?
 - What types are inferred for type parameters `A`, `B` and `C` of `transitive` during its implicit search?

#### test02
 - What is the signature of the method `sorted` as a member of the qualifier `listOfFoo`?
 - Which implicit argument is applied as an argument to the `sorted` method of `listOfFoo`?
 - What implicit(s) are being verified by the compiler as possible arguments to the `sorted` method? Why does the compiler select the one you stated in your previous answer?

 - Which implicits are taken into account when searching for implicit argument of the `sorted` method of `listOfBar`?
 - Can the typechecker use the same argument as in the case of `listOfFoo`?
 - What is the sequence of implicits that causes the divergence?
 - Briefly give a possible fix for the underlying problem (so that you can call `sorted` method on `listOfBar`)?

#### test03
 - What is the type of `set1`?
 - Does the application `++` present in `set1` involve applying implicit arguments at any point of typechecking the body of the value?

 - What is the type of the member selection in `SortedSet.empty`?
 - What is a sample sequence of implicits which would lead to the divergence?



### Implicits14 (source at *implicits/Implicits6.scala*)
For the questions below we will be working with a `map` method. For `mutable.LinkedList` as well as `BitSet` it is first defined in [GenTraversable](http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/library/index.html#scala.collection.GenTraversable). It's full signature method is pretty hairy (see scaladoc for details): `def map[B, That](f: (A) => B)(implicit bf: CanBuildFrom[GenTraversable[A], B, That]): That` but typically you do not have to worry about it. Notice only the implicit parameter of type `CanBuildFrom` (if you are doing the tutorial step-by-step then we already discussed it in the [inference section](us-inference.html).

`CanBuildFrom` trait defined in Scala [docs](http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom) allows to build collections in a reusable way. `CanBuildFrom[-From, -Elem, +To]` (with type paramers) defined a way to build a collection, from a collection of type `From` (e.g. `Seq[_]`) having elements of type `Elem` (e.g. `Float`) to a target collection `To` (e.g. `List[Float]`).

This will be important to answer the questions below.

- Whay is the type of `a`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `b`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `c`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 

- Whay is the type of `d`? What implicit argument has been provided by the compiler? If the final type differs from the original collection explain why. 