---
layout: page
title: "Implicit search and type debugger"
---
{% include JB/setup %}

## Implicit search ##
It is always frustrating to find out that the implicit you have defined in some context is not found by the compiler or, worse, that you get some non-trivial ambiguity which prevents the compiler from selecting it.
Apart from logging which spits out incomprehensible amounts of data, there exists currently no way to diagnose those problems. 

## Type Classes ##

We will focus on one of the most popular design patterns used with implicits: **Type Classes**. This construct allows us to support ad-hoc polymorphism. **TypeClasses.scala** playes around with this pattern in the context of serialization.

We have type class `Serializer` which provides methods `pickle` and `unpickle` for serialization and de-serialization, respectively. We provide a couple serializers (implementations are irrelevant) like `genSerializer`, `treeSerializer`, `longTreeSerializer` or `longTreeSerializer`. Later we want to test those serialization techniques using `testSerialization` method in `test`.

Why out of all those tests only `testSerialization(a)` fails? After all implicits are never in direct outer scope? It turns out that the implicit search scope is not trivial and you can use it to build interesting constructs.

Typechecking each of the test calls start by typechecking the application, which in turns requires the inference of type parameter `T` for `testSerialization` (how?). Only at that point the typechecker will try to answer the question `Can we find and apply any missing implicit arguments?`. This is the point where implicit search kicks in. Using type debugger try to answer questions like a) which implicit(s) were selected b) why was there/was not there ambiguity between implicits c) was it what you were expecting?

