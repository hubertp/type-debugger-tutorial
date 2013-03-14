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

The initial typechecking is only used for providing the users with a feedback about real compiler errors in the code and does not do the actual debugging. The latter is required for [targeted debugging](#TargetedDebugging) of typechecking.

The user interface consists of two main parts:
 - Visualization of the typechecking on the left
 - Code editor on the right

The initial view of the former contains only a single node since we have not initiated any debugging yet.

Get some example files for the tutorial (this is also the repository with the sources for this tutorial):

    git clone git://github.com/hubertp/type-debugger-tutorial.git
    cd type-debugger-tutorial

Start the tutorial by running **scalad** on our set of simple programs 

    scalad resources/code/Show.scala resources/code/FindDefinition.scala resources/code/SubtypingFun.scala


In **Show.scala** we have a method `show` is called twice on an Int. For users who rely on implicit conversions in every day programming it is obvious why `bar` will compile whereas `foo` will not. But how does the typechecker really makes such a decision? At which point does it finally fail? In the following sections we will show how to explore those questions on your own.

### Targeted debugging ### {#TargetedDebugging}
Understanding the typechecking of full programs is unmanageable therefore Type Debugger allows to select a fragments of the code which are of interest to the user. To start targeted debugging simply select a fragment of the program in the editor.

## Navigation ##

Let's select method `foo` (including its body) in **Show.scala** to start debugging:

{% highlight scala linenos %}
def foo(): Unit = {
  val x: Int = 12
  x.show()
}
{% endhighlight %}

This will:
 - Expand a fragment of the full proof tree with typechecking steps
 - Expand the tree up to a point where compiler decided to report an error

Typechecking is essentially a proof that (hopefully) ends up with a simple statement of a form *This program is type correct*. To prove this, we have to satisfy multiple nested sub-clauses which on the other hand may require further typechecking adjustments/verification. Hence the visualization in a form of *inverted* tree (i.e. the conclusion is at the bottom).

It is useful to know that:

 - Basic goal information is as succinct as possible in order to reduce the potential blow up in terms of amount of information

 - Hovering over goals will highlight corresponding code fragment in the editor (if possible)

 - Single-clicking on the goals will automatically expand their premises (if any). Clicking on an already expanded goal will collapse the details of its premises. This mechanism is inverted if you are exploring the proof tree downwards

 - Clicking `Cltr` while hovering over the goal will give you a tooltip with more detailed information (if possible). To close, click outside of the tooltip

 - You can zoom in/out the proof using the usual scrolling techniques

 - You can use code selection in the editor for coarse-grained navigation. Typechecking will not be repeated if the overlapping tree has been debugged before

 - Typechecking process **always** goes from left to right. Hence if you are lost or just looking for a quick answer then looking at the last premise is a good thing. Additionally hovering over many of the *question type* goals will display a quick answer at the bottom of the tool

 - Blue, round goals represent final statements that don't have any further premises. The full color legend is visible under

 - If you *feel* that some information does not carry enough information or goals hides some information then notice if the cursor changed from a normal hand to cross-hair and the status bar at the bottom does not display additional information. Right-click to reveal more information and see [later section](#AdditionalInfo).

Therefore (if you selected the full `foo` method), in the above example clicking `Typecheck object member 'method foo'` will expand its parent `Can we type Test object?` as you could expect. Notice that the premise of the latter (`bar` method typechecking) is gray and typechecking for it has been omitted as it was not on the direct path to the selected code.

<img src ="{{BASE_PATH}}assets/type_debugger01.jpg"
alt="Typechecking proof for the selected foo method" width="700" height="450"
title="Typechecking proof for the selected foo method" class="img">
</img>

### Digging deeper ### {#DiggingDeeper}

Brief goal names often do not convey enough information to understand the whole process therefore we have tooltips.
 - Hover over the `Typecheck object member 'method foo'` goal and click `Ctrl`- Type debugger show a tooltip for the specific goal with more detailed information

That particular goal will give:
 - A pretty-printed [Abstract Syntax Tree][asts] that the compiler needs to typecheck
 - Expected type enforced by the context (**?** represents a wildcard, no context information)
 - Initial and final type at the end of typechecking the expression

A typechecking of a goal will typically consist of three main premises (of course that depends on errors, previous usage, visibility) that:

- **Verify the signature of the tree**. The first time typechecker encounters a definition it has to verify that it can be used correctly. Therefore verification can often pop-up in some weird locations during typechecking e.g. a class type that is in the bounds of some other type will be verified at that point even though it is declared some time later. This step is done only once per definition
- **Type the tree**. Typechecker will attempt to assign the most concrete type to the tree based on the current context and expected type
- **Adapt the type of the tree to the expected type**. At this point typechecker will make sure that the type of the tree conforms to the expected type (in the previous step it might only influence but enforce it). This might include applying arguments to a method that has implicit parameters, inference of a concrete type for an expression or an attempt to fix failed subtyping for the expected type

For instance `Typecheck object member 'member foo'` verifies the signature of the method by typechecking the return type `scala.Unit` and then tries to answer the question `Can we type method foo definition` by typechecking the body of the definition (adaptation is not shown, since the latter had errors).

Back to the error: 
 - `Typecheck last statement` highlights `x.show()`
 - Exploring its premises gives us information about `Typechecking function` (for `x.show`), `Can we type member selection?` (for `x.show`) and its premises: `Typecheck qualifier` (for `x`) and `Can we find a specific member of the qualifier?` (for `.show`)
 - The latter reveals the true failure (red goal) in its sub-goal - `show is not a member of Int`
 - Notice the operation before reporting an error: `Type of qualifier does not have a required member. Can we adapt qualifier to member and its arguments?`. This triggers an implicit search and fails (more detailed example with implicit search is given in [section](implicits.html).

Compare with error-free code by selecting method `bar`:

{% highlight scala linenos %}
    def bar(): Unit = {
      implicit def conv(a: Int): Show = new Show(a)

      val x: Int = 12
      x.show()
    }
{% endhighlight %}

The difference is:
 - `Type of qualifier does not have a required member. Can we adapt qualifier to member and its arguments?` succeeds by finding the right implicit view.
 - We need to apply the view to the qualifier and typecheck it in `Typecheck application of inferred view that adapts qualifier`
 - `Typecheck member selection with the adapted qualifier` where the adaptation-free qualifier previously failed

More examples related strictly to implicit search can be seen in the [implicits section](implicits.html).

## Additional features ## {#AdditonalInfo}

### Marking goals as *sticky*

Since we have just compared almost identical subtrees it might useful to have them next to each other case by case (which we leave to the user to play around). You can mark the goal as *sticky* so that it does not collapse if we want to compare/leave it for further investigation. Just click the goal with `Cltr+Shift` (undoing the operation is done the same way). Expanded error goals are *sticky* as well so you can treat them in the same way.


### Hidden goals ### 
*But you still didn't show how the implicit conversion was really found?*. That's true, some of the goals are initially hidden from the user's perspective because 

 - The amount of full information will distort the general view of the typechecking
 - Some goals require better knowledge of Scala and type systems in general and might be intimidating for for beginner users

Notice that goal `Can we infer view that adapts qualifier to member?` from typechecking `foo` will change the cursor type when hovered over. This indicates that it provides additional option(s) when you right-click on it. In this particular case it will reveal hidden sub-goal `Search for an implicit view` that does all the hard work related to implicit conversion search (double-click will also do the work as you will find out some goals provide more options).

It is interesting to see the list of all the implicit values and their origins that are considered each time an implicit search is involved. 
It is possible to make those advanced goals visible all the time by selecting appropriate type on the `View goals -> Hidden goals` menu.

### Scala Language Specification ###

Type debugger is an excellent tool to explain the language in terms of the [Scala Language Specification](sls). Enable it through `View goals -> Include Scala Language Specification information`.

That provides references to the document, if possible in the tooltip display for some of the goals. Notice that at the moment references will be relatively sporadic and need a bit more work. Also for the purpose of type debugger, specification would have to provide better indexing for the sections in order to be more precise.

## ... a little help in navigation would be nice ##
Visualization of typechecking explains decisions made by the compiler, but a lot of the exploration burden is still on the shoulders of the user. This may be an acceptable cost if you are dealing with simple programs (like those in the tutorial) but would be discouraging for more realistic scenarios. Therefore some of the goals provide additional help.

The following (small) list of helpers, available through right-click on some of the goals, should already improve the experience of navigating between the goals and decrease the effort necessary to understand particular typing problems. Whenever help is available to the users, the cursor will change. The list of helpers is by means no means exhausted and in fact should be more easily scriptable in the future.

#### Where was XYZ defined?
Let's assume an example presented in **FindDefinition.scala** (just switch the tab at the top). The dummy `CachedResult` class performs some imaginary calculation and stores the result. The class relies on correct type inference for members `x` and `initial`. Since one of the assignments (see red squiggle)

{% highlight scala linenos %}
    x = calcSth(a, b)
{% endhighlight %}

leads to an error we would like to see what is wrong there, so select the above fragment.

It seems that the assignee `x` (or rather its setter `Typecheck application involving variables setter`) has a very weird type. At which point and how was this type inferred?
Finding out this information on the selected proof tree is already a tedious task. However you know that you assign to `x` so typing the assignment in `Can we type assignment?` should first typecheck it (`Typecheck assignee`), right? The problem is that the typechecker at this point has already inferred type of `x` and only looks its up in the scope in `Can we find the definition of 'x'?`. However whenever you encounter such node you can ask the type debugger (right-click) and an available option `Show source of identifier's type` will trigger an expansion that leads to the point where variable's type (or in fact most of definition's type) was defined/inferred.

To practice, select the body of the `reset` method:

{% highlight scala linenos %}
      val i = initial
      // more calculations
      x = new Result(i)
{% endhighlight %}

Let's say we want to find out how the type of `i` got inferred:
 - You navigate to the first statement responsible for the assignment
 - Expand `Can we verify the signature of a value i?`
 - By expanding further you reach the end goal `Can we find the definition of initial?` (see bottom of the panel for the quick answer)
 - Right-click on the goal, `Show source of identifier's type` and let the type debugger do the hard work
 - It is interesting to see that finding that location would be non-trivial since `initial` was first used as an argument while creating a new instance of `Result` so it is hidden somewhere deep in the typechecking tree

#### Where did the initial error occur? {#FindError}
As you could see in the very first example of **Show.scala** for

{% highlight scala linenos %}
    def foo(): Unit = {
      val x: Int = 12
      x.show()
    }
{% endhighlight %}

type debugger will place an error sub-goal directly as a premise of `Typecheck last statement`. Although correct it does not really say much where did the real error originate from. 

So:
 - Right-click on this failed premise and pick `Where did the underlying error occur?`
 - Type debugger will lead to the underlying goal that could not be satisfied. This is a convenient shortcut if problem is hidden somewhere deep.

Notice that although convenient in this case it won't work all the time (i.e. it won't expand to the place where the error was first reporter in e.g. implicit search failures). This is only a temporary limitation of the current prototype.

#### Why did this subtyping test fail?
Most common type error is a type mismatch where two types are not subtypes. Depending on the context such test may be non-trivial and visualizing it can be long and confusing. Therefore it would be useful to ask at which point the subtyping test did actually go wrong, right?

Consider an example from **SubtypingFun.scala** where we apply member `foo` to a value `x1` in

{% highlight scala linenos %}
  def test() {
    foo(x2)
    foo(x1)
    
  }
{% endhighlight %}

Select `x2` first:
 - Typechecking argument in the application has two premises: `Can we find the definition of 'x2'?` and `Can we adapt expression to the expected type A => Object?`
 - The latter hides some subgoals so right-click the correct action
 - Type debugger reveals the subtyping check `A => A <: A => Object` that succeeds (feel free to see how the subtyping algorithm works in Scala)

Select `x1` now:
 - Type debugger expands subtree involving the error. We are only interested in the initial attempt at typechecking application
 - Expand `Can we adapt expression to the expected type A => Object` to see that it will fail
 - Right-click on the goal and ask it why *Where did the subtyping relation go wrong?* action. It should be obvious now that contravariance is the culprit here.

#### Where does this expected type come from? {#ExpectedType}
Let's have a look again at the familiar `CachedResult` example in **FindDefinition.scala** with the invalid assignment:

{% highlight scala linenos %}
    x = calcSth(a, b)
{% endhighlight %}

and find out the source of the expected type in the type mismatch error:

 - Select the fragment to debug
 - Right-click on the type error and find its real origin (as you learned in one of the [previous sections](#FindError))
 - Now you know that application of `x`'s setter to the `calcSth` failed while typechecking the argument. The goal that failed is `Can we adapt expression to the expected type 'Result[Long]'?`
 - Where does this expected type come from? Why isn't it `Result[Int]` like we wanted it to be? 
 - Right click on that goal and select the action *Where does the expected type come from?* help to find out
 - Type debugger does the hard and reveals the goal(s) that directly enforce and/or affect the expected type. More info on the found goal (`Ctrl+hover`) attaches a more detailed explanation on why that particular one was selected
 - The goals will remain expanded until you call **Navigation heuristics -> Clear helpers** 

A more realistic example in **TypeInference.scala**. It provides a simple example of incrementing all elements using `foldRight` (we use `fold` because of its interesting signature only).
Compiling the example 
    
{% highlight scala linenos %}
    val a = List(1,2,3)

    // increment all elements in a using fold
    a.foldRight(Nil)((elem: Int, xs: List[Int]) => (elem + 1) :: xs)
{% endhighlight %}


gives us a compilation error:

{% highlight scala linenos %}
    resources/code/TypeInference.scala:9: error: type mismatch;
     found   : List[Int]
     required: scala.collection.immutable.Nil.type
        a.foldRight(Nil)((elem: Int, xs: List[Int]) => (elem + 1) :: xs)
                                                                  ^
    one error found
{% endhighlight %}

Where did the `Nil` type come from? After all we are just doing a `foldRight` on a list of `Int`'s!.

Select the offending fragment to find out:

{% highlight scala linenos %}
    (elem + 1) :: xs
{% endhighlight %}

 - We are in the middle of an application in the function body. For more context feel free to roam around the goals by asking type debugger for more information regarding goals and it will display intermediate [trees](asts).
 - Right-click on the adaptation goal `Can we adapt expression to the expected type scala.collection.immutable.Nil.type?` and ask it about the expected type
 - Remember that want to find out the source of the `Nil` type and the expanded tree is non-trivial. It should be clear now that `Nil` is inferred for type parameter `B` in `a.foldRight[B](Nil)` application
 - The answer is surprising because it is indeed only our first `Nil` argument which affects the expected type. By the time we apply our anonymous function to the second list of arguments all the type arguments are already inferred.
 - In the next section we will investigate in detail how the instantiation for `B` was inferred

#### Where do all these constraints come from?
In the previous example we were answering the question of where did the expected type come from. Type debugger can also help to understand how type arguments are inferred by the compiler.

Search from the previous section highlighted goal `Can unresolved type variables be finally inferred, using information from the just performed conformance tests?`. What does that mean?

Type variables are temporary types that collect constraints on the type parameters (in this case `B` of `foldLeft`). However at some point we have to solve them and infer precise instantiations.

The above goal has two children:
 - `Infer instantiation for type variable ?B ...?` 
 - `Inferred mapping of type variables...` which shows that the inferencer will substitute `Nil` for `B`. That's too late so let's have a look at the former goal in detail

 Ask type debugger for more information with `Ctrl` and it will mention that at this point constraints on type variables lower and upper bounds. Let's ask the where do its constraints come from by right-clicking the goal.

 - That reveals a close-by goal where the actual conformance (subtyping) check is done between the type of the argument and the formal parameter of the function at that position. In other words `Nil <: ?B` registers a lower bound constraint on the type variable representing type parameter `B`.
 - Since type variable is in non-contravariant position we will calculate least upper bound of the lower bound constraints i.e. `Nil <: ?B`

Finding the constraint source manually for this example was easy but (unfortunately) it is usually not the case for more complicated scenarios.

[stlc]: http://en.wikipedia.org/wiki/Simply_typed_lambda_calculus
[asts]: http://docs.scala-lang.org/overviews/reflection/symbols-trees-types.html#trees
[sls]: http://www.scala-lang.org/docu/files/ScalaReference.pdf