---
layout: page
title: "Overview"
description: "Introduction to type debugger features"
tagline: "or Where did this type come from?"
---
{% include JB/setup %}

Before starting the tutorial make sure you have the latest version of the tool, as described at [Tool setup](toolsetup.html) instructions.

## User Interface ## {#UserInterface}

    scalad path/to/files*


Running the command does two things

1. starts a user interface that will provide the visualization
1. runs the complete typechecking (exactly the same like standard `scalac`) in the background

The initial typechecking is only used for providing the users with a feedback about real compiler erros in the code and does not do the actual debugging. The latter is required for [targeted debugging](#TargetedDebugging) of typechecking.

The user interface is divided into two parts: visualization of the typechecking on the left and code editor on the right. The initial view of the former contains only a single node since we have not initiated any debugging yet.
Let's run **scalad** on our set of simple programs provided with the tutorial

    scalad resources/code/Show.scala resources/code/FindDefinition.scala resources/code/SubtypingFun.scala


There we have two cases where we try to call a method `show` on an integer. For users who rely on implicit conversions in every day programming it is obvious why `bar` will compile whereas `foo` will not. But how does the typechecker really makes such a decision? At which point does it finally fail? In the following sections we will show how to answer those questions.

### Targeted debugging ### {#TargetedDebugging}

In order to minimize the overhead of instrumentation and exploration of the typechecking proof type debugger provides a possibility of debugging only fragments of the code which are of interest to the user. Compiler will only display typechecking of fragments necessary to satisfy that request (including those which are referenced but are not on the direct path). Hence the resulting proof tree will be a subset of the original one.
One starts targeted debugging simply by selecting a fragment of the program in the editor.

## Navigation ##

Let's select method `foo` in **Show.scala** to start debugging. 
<!-- todo --> 

{% highlight scala linenos %}
def foo {
  val x: Int = 12
  x.show()
}
{% endhighlight %}


This reveals a fragment of the full proof tree explaining the typechecking steps done for checking that particular code fragment. Since we have an error within that particular method, the expanded proof tree will conveniently expand it up to a point where compiler decided to give up and reported an error.

Why use tree and not some other data structure or method? Typechecking is essentially a proof that (hopefully) ends up with a simple statement "This program is type correct". To prove this, we have to satisfy multiple nested sub-clauses which on the other hand may require further typechecking adjustments/verification. Therefore we decided to visualize what the compiler is doing in a form of *inverted* tree (i.e. the conclusion is at the bottom). Users who have done formal proofs by hand should find this image familiar (for instance typechecking/type inference for [Simply Typed Lambda Calculus][stlc]).

Basic goal information is as succinct as possible in order to reduce the amount of noise within the visualization. In order to navigate the tree it is useful to know that:

1. Hovering over goals will highlight corresponding code fragment in the editor (if possible).

2. Clicking on the goals will automatically expand their premises (if any). Clicking on an already expanded goal will collapse the details of its premises. This mechanism is inverted if you are exploring the proof tree downwards.

3. Hovering over goals with `Cltr` on will give you a tooltip with an additional information regarding the goal (if possible).

4. You can zoom in/out the proof

Therefore, in the above example clicking `Typecheck object member 'method foo'` will expand its parent `Can we type Test object?` as you could expect. Notice that two other of its premises (`constructor` and `bar` method typechecking) are grey and typechecking for them has been omitted. This means that they were not on the direct path to the selected code and therefore their debugging was not necessary. 

You can also use code selection in the editor for coarse-grained navigation. That will always focus your proof on the goal that covers the typechecking of the selected area.

### Digging deeper ### {#DiggingDeeper}

Brief goal names often do not convey enough information to understand the whole process. Therefore let's hover over the `Typecheck object member 'method foo'` goal and click `Ctrl`. That will reveal a tooltip for the specific goal with more detailed information (specific to each proof goal). In that particular case it will give a pretty-printed [Abstract Syntax Tree][asts] that the compiler needs to typecheck, expected type enforced by the context, initial and final type at the end of typechecking.

You will notice that a typechecking goal will often have three main premises (depending on errors, previous usage, visibility):

- Verify the signature of the tree
- Type the tree.
- Adapt the type of the tree to the expected type.

For instance `Typecheck object member 'member foo'` verifies the signature of the method by typechecking the return type `scala.Unit` and then tries to answer the question `Can we type method foo definition` by typechecking the body of the definition (adaptation is not shown, since the latter had errors).

Let's now focus on the branch of the proof that lead to an error: `Typecheck last statement` highlights correctly `x.show()`. Exploring its premises gives us information about `Typechecking function` (for `x.show()`), `Can we type (potential) member selection?` (for `x.show`) and its premises: `Typecheck qualifier` (for `x`) and `Can we type member selection?` (for `.show`). The latter reveals the true failure (red goal) in its sub-goals - `show is not a member of Int`. Something interesting however happens - before compiler reports an error it tries to satisfy the goal `Type of qualifier does not have a required member. Can we adapt qualifier to member and its arguments?` which triggers an implicit search and fails (more use cases for implicits are given in [section](implicits.html).

Let's select method `bar` and compare it with what we got for `foo`:

{% highlight scala linenos %}
    def bar(): Unit = {
      implicit def conv(a: Int): Show = new Show(a)

      val x: Int = 12
      x.show()
    }
{% endhighlight %}

The resulting tree is almost identical with a small exception: `Can we adapt qualifier to member and its arguments` goal now succeeds and finds the right implicit view. Compiler will typecheck the application in `Typecheck application of inferred view that adapts qualifier` and proceed with member selection in `Typecheck member selection of the adapted qualifier`. 

### Marking goals as *sticky*

It might be also convenient to select code that covers both methods and compare each of the goals one by one (we leave it to the user to play around).
To help with that we can mark the goal as *sticky* so that it does not collapse if we want to compare/leave it for further investigation. Just click the goal with `Cltr+Shift` (undoing the operation is done the same way). Error goals are typically *sticky* so you can treat them in the same way.

## Additional features ##

### Hidden goals ###
*But you still didn't show how the implicit conversion was found?*. That's true, some of the goals are initially hidden from the user's perspective because 

* the amount of full information will distort the general view of the typechecking.
* some goals require a better knowledge of Scala and might not be suitable for beginner users.

Notice that goal `Can we infer view that adapts qualifier to member?` from typechecking `foo` will change the cursor type when hovered over. This indicates that it provides additional option(s) when you right-click on it. In this particular case it will reveal hidden sub-goal `Search for an implicit view` that does all the hard work related to implicit conversion search.

It is interesting to see the list of all the implicit values and their origins that are considered each time an implicit search is involved. 
It is possible to make those advanced goals visible all the time by selecting appropriate type on the `View goals -> Filtering` menu.

### Scala Language Specification ###

Type debugger is an excellent tool to explain the language in terms of the [Scala Language Specification](sls). You can enable it through `View goals -> Include Scala Language Specification information`. After that full-info tooltip (`Ctrl`) will provide references to the document, if possible. Notice that at the moment references will be relatively sparse and need a bit more work. Also for the purpose of type debugger, specification would have to provide better indexing for the sections in order to be more precise.

## ... a little help in navigation would be nice ##
Although visualization of the typechecking process already explains decisions made by the compiler, a lot of an exploration burden is still on the shoulders of the user. This may be an acceptable cost if you are dealing with simple programs (like those in the tutorial) but would be discouraging for more realistic scenarios. 

Therefore in order to help with understanding the whole process some of the goals provide additional help. The following (small) list of helpers should already improve the experience of navigating between the goals and decrease the effort necessary to understand particular typing problems. Whenever help is available to the users, the cursor will change. The list of helpers is by means no means exhausted.

#### Where was XYZ defined?
Let's assume an example presented in **FindDefinition.scala**. Here our dummy `Calculate` class performs some imaginary calculation and stores the result. The class relies on correct type inference for members `x` and `initial`. Since one of the assignments

{% highlight scala linenos %}
    x = calcSth(a, b)
{% endhighlight %}

leads to an error we would like to see what is wrong there. Selecting the fragment gives us a brief overview of the process of typechecking the assignment.

It seems that the assignee `x` (or rather its setter) has a very weird type. At which point and how was this type inferred?
Finding out this information on the selected proof tree is already a tedious task. Therefore, let's select the `update` method, go to the assignee goal `Typecheck assignee` of the assignment and right-click on `Can we find the definition of 'x'?` - an available option `Show source of identifier's type` will trigger an expansion that leads to the point where variable's type was defined. To find out where does the expected type come from we will use [expected type](#ExpectedType) search later.

For a similar identifier search let's select the body of the `reset` method

{% highlight scala linenos %}
      val i = initial
      // more calculations
      x = new Result(i)
{% endhighlight %}

This time the assignment is type correct but we would like to still find out exactly how does the type of `i` get inferred. We navigate to the first statement of the body and then to the signature verification goal `Can we verify signature of a value 'i'?`. But after two more expansions we are stuck since it gets the type `initial` by some magic in `Can we find the definition of 'initial'?`. Where and how do we get it? Where exactly during typechecking process do we infer its type? Let's use the provided helper once again and right-click on the node. The expanded sub proof is already non-trivial so the action reduces the burden of the proof navigation from the user.

#### Where did the initial error occur?
As you could see in the very first example of **Show.scala**

{% highlight scala linenos %}
    def foo(): Unit = {
      val x: Int = 12
      x.show()
    }
{% endhighlight %}

will place an error sub-goal directly as a premise of `Typecheck last statement`. Although correct it does not really say much where did the real error originate from. However you can right-click on this failed premise and pick `Where did the underlying error occur?`. This will lead to the underlying goal that could not be satisfied which is a convenient shortcut if problem is hidden somewhere deep.

#### Why did this subtyping test fail?
One of the common type errors is a type mismatch where two types are not subtypes. Depending on the context such test may be not trivial and visualizing it can be long and confusing. Therefore it would be useful to ask at which point the subtyping test did actually go wrong, right?
Consider an example from **SubtypingFun.scala** where we apply member `foo` to three similar function values `x1`, `x2` and `x3` in

{% highlight scala linenos %}
    foo(x1)
    foo(x2)
    foo(x3)
{% endhighlight %}


Why is the second application incorrect whereas the other two are fine in the above example? Let's expand proof for the application involving `x3`, to compare with the others. Typechecking argument in the application has two premises: `Can we find the definition of 'x3'?` and `Can we adapt expression to the expected type A => Object?`. The latter hides some subgoals so let's right-click the correct action. That reveals the subtyping check `A => A <: A => Object` which succeeds (the algorithmic subtyping done here can become pretty large for more complicated types).

Let's now shift our focus to the erroneous application of `foo` to `x2`. Here again we have to adapt the type of the argument to the expected type but it fails. Let's right-click on the adaptation goal `Can we adapt expression to the expected type A => Object?` and ask it why with *Where did the subtyping relation go wrong?* action. It should be obvious now that contravariance is the culprit here.

#### Where does this expected type come from? {#ExpectedType}
Let's have a look again at the familiar `Calculate` example in **FindDefinition.scala** with the invalid assignment:

{% highlight scala linenos %}
    x = calcSth(a, b)
{% endhighlight %}

selecting the fragment automatically expands sub proof responsible for the erroneous assignment. Let's right-click on the type error and find out where exactly did it come from during the typechecking process.
This reveals that application of a setter to the `calcSth` call failed while typechecking the argument. More precisely it failed while checking the type of the argument in goal `Can we adapt expression to the expected type 'Result[Long]'?`. A typical reaction would be to ask where does this expected type come from? Why isn't it `Result[Int]` like we wanted it to be? Let's right click on that adaptation goal and find out why by selecting the *Where does the expected type come from?* help. This action expands the typechecking proof to reveal the goal(s) that directly enforce and/or affect the expected type. More info on the found goal (`Ctrl+hover`) attaches a more detailed explanation on why that particular one was selected.

The above example was pretty straightforward to keep things simple. So let's analyze something slightly more interesting. A code in **TypeInference.scala** provides a simple example of incrementing all elements using `foldRight` (we use `fold` because of its interesting signature only).
Compiling the example 
    
{% highlight scala linenos %}
    val a = List(1,2,3)

    // increment all elements in a using fold
    a.foldRight(Nil)((xs: List[Int], elem: Int) => (elem + 1) :: xs)
{% endhighlight %}


gives us a compilation error:

{% highlight scala linenos %}
    resources/code/TypeInference.scala:9: error: type mismatch;
     found   : List[Int]
     required: scala.collection.immutable.Nil.type
        a.foldRight(Nil)((xs: List[Int], elem: Int) => (elem + 1) :: xs)
                                                                  ^
    one error found
{% endhighlight %}

Where did the `Nil` type come from? After all we are just doing a `foldRight` on a list of `Int`'s!. Let's run type debugger on it and select the offending fragment:

{% highlight scala linenos %}
    (elem + 1) :: xs
{% endhighlight %}

This reveals that we are in the middle of an application in the function body. For more information on how typechecker transforms such expressions feel free to roam around the goals by asking type debugger for more information regarding goals and it will display intermediate [trees](asts). At the moment we are only interested where did this expected `Nil` type come from. So let's right click on the adaptation goal `Can we adapt expression to the expected type scala.collection.immutable.Nil.type?` and ask it.
The answer can be surprising because it is indeed our first `Nil` argument which affects the inferred type. By the time we apply our anonymous function to the second list of arguments all the type arguments are already inferred. Hence we believe that With the visualization it is possible to understand the limitations of the current type inference.

#### Where do all these constraints come from?
In the previous example we were answering the question of where did the expected type come from. Type debugger can also help to understand how type arguments are inferred by the compiler.

Search from the previous section highlighted goal `Given results from conformance (subtyping) tests with expected type, can type variables be finally resolved?`. Type variables are temporary types that collect constraints on the type parameters. However at some point we have to solve them and infer precise instantiations. The above goal has two children: `Infer instantation for type variable ?B ...?` and the result of such type inference. Let's ask the former where do its constraints come from by right-clicking the goal. That reveals a close-by goal where the actual conformance (subtyping) check is done between the type of the argument and the formal parameter of the function at that position. In other words `Nil <: ?B` registers a lower bound constraint on the type variable representing type parameter `B`.
Finding the constraint source manually for this example was easy but (unfortunately) it is usually not the case for real programs.

[stlc]: http://en.wikipedia.org/wiki/Simply_typed_lambda_calculus
[asts]: http://docs.scala-lang.org/overviews/reflection/symbols-trees-types.html#trees
[sls]: http://www.scala-lang.org/docu/files/ScalaReference.pdf