---
layout: page
title: "Implicit search"
---
{% include JB/setup %}

## Implicit search ##
It is always frustrating to find out that the implicit you have defined in some context is not found by the compiler or, worse, that you get some non-trivial ambiguity which prevents the compiler from selecting it.
Apart from logging which spits out incomprehensible amounts of data, there exists currently no way to diagnose those problems. 

## Implicit view ##
In order to understand simple implicit search let's go back to the example from the tutorial in **Show.scala** and select method `foo`. Because the qualifier `x` of type `Foo` does not have a member `show` the compiler will try to figoure out if `x` cannot be adapted in `Can we infer view that adapts qualifier to member?` So let's expand it and we get two sub-goals `Search for an implicit view` and `Search for a by-name implicit view`. What is the difference? The former will search for members matching type `Int => ?{ def show: ?}` and the latter `(=> Int) => ?{ def show: ?}` and each are checked in turn with the same number of scopes.

If we go deeper there are other two subgoals `Is there an applicable implicit in the current scope?` and `Is there an applicable implicit corresponding to the expected type?`. Since the information here is a little bit more advanced, you will have to expand goals manually often in order not to be swamped with information. Even though you don't explicitly import any values, type debugger displays that there are a lot of *potentially eligible imported implicits* in the former goal. Where do they come from? Essentially all of them come directly and indirectly through `scala.Predef` and have to be checked for applicability each time we initiate an implicit search.

According to the spec we would also look for all the implicits defined in the companion of `Int`. Hence the latter goals verifies `int2double`, `int2float` and `int2long`. If you ever wondered what is going on behind the scenes with implicit search, type debugger is your answer.

## Type Classes ##

We will focus on one of the most popular design patterns used with implicits: **Type Classes**. This construct allows us to support ad-hoc polymorphism. **TypeClasses.scala** playes around with this pattern in the context of serialization.

We define a type class `Serializer` which provides methods `pickle` and `unpickle` for serialization and de-serialization, respectively. 

{% highlight scala linenos %}
    trait Serializer[T] {
      def pickle(x: T): Array[Byte]
      def unpickle(x: Array[Byte]): T
    }
{% endhighlight %}


We provide a couple serializers (implementations are irrelevant) like `genSerializer`, `treeSerializer`, `longTreeSerializer` or `strTreeSerializer`. Later we want to test those serialization techniques using `testSerialization` method in `test`.

Why out of all those tests only `testSerialization(a)` fails? After all implicits are never in direct outer scope? The localisation of implicit search scope is not trivial and you can use it to build interesting constructs, much more powerful to what we have presented.

Typechecking each of the *testSerialization\** calls starts by typechecking the application, which in turns requires the inference of type parameter `T` for `testSerialization` (how?). Only at that point the typechecker will try to answer the question `Can we find and apply any missing implicit arguments?`. This is the point where implicit search kicks in. Using type debugger try to answer questions like a) which implicit(s) were selected b) why was there/was not there ambiguity between implicits c) was it what you were expecting?

