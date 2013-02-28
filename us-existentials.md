---
layout: page
title: "Questions: Existentials"
tagline: "Questions related to existential types"
---
{% include JB/setup %}

### Existentials01 ###

#### test1 ####
 - What is the inferred type `A` for `case ToStore(v1, w1)`? And therefore what is the type of the values inside the body of the match?
 - Are `v1` and `w1` arguments typechecked with any expected type? Does the application of `process` have any expected type?
 - What are the lower and upper bound constraints on type parameter `T` and how were they collected?
 - How did the compiler infer the type of `T`?

#### test2 ####
 - What is the inferred type `A` for `val ToStore(v1, w1) = ` match?
 - What is the type of the value `v1`? Why?
 - What is the type of the value `w1`? Why?
 - What are the lower and upper bound constraints on type parameter `T` and how were they collected?
 - How did the compiler infer the type of `T` to be `Any` (in the error message)? Should argument `v1` also report a type mismatch?

#### test3 ####
 - What is the type of `elem` ?
 - What is the type of `elem.v` member selection?
 - What is the type of `elem.writer` member selection?
 - What are the lower and upper bound constraints on type parameter `T` and how were they collected?
 - Why does the error message includes `(some other)` again one of the types? Why is that possible?
 - Does making the type parameter `A` in `Writer` invariant changes the inferred type?
 - Does making the type parameter `A` in `Writer` covariant changes the inferred type?


### Existentials02 ###
In the given example, only second application (involving `foo`) reports a type mismatch.

 - The inferred type for the `Seq` in the second application is `S[_ >: String with Int]`.  What is the inferred type of `Seq` for the fourth application?
 - What are the lower and upper bound constraints on type parameter `A` of the sequence `Seq[A]` in second application? How does the compiler infer the type of `A` from that?
 - What are the lower and upper bound constraints on type parameter `A` of the sequence `Seq[A]` in second application? How does the compiler infer the type of `A` from that?
 - Why is `Seq[Existentials02.this.S[_ >: String with Int]]` not a subtype of `Seq[Existentials02.this.S[X]] forSome { type X }`
 - Function `def bar(as: Seq[A[_]]) = true` is equivalent to `foo` and/or to `otherFoo` or none of them? Why?

