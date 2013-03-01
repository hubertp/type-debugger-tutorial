---
layout: page
title: "User studies"
tagline: "Let's solve some puzzles"
---
{% include JB/setup %}

In order to verify the applicability of our prototype to dealing with type system problems in Scala we will be performing user studies on real problems. We will cover a range of issues that a typical programmer can experience during everyday programming. Some of the topics include:

- basics that include simple member selection or typechecking application
- subtyping
- type inference (also understanding its limitations for higher-kinded types)
- implicit search
- existentials
- pattern matching (in a limited form)
- variance

Target audience for our user studies ranges from beginners to advanced Scala users. Therefore, depending on the skills of the programmer, some of the posted questions might seem trivial but others too hard. In fact some of problems were inspired by questions posted on Scala mailing list or Stackoverflow.

<!--
In order to be objective all users will be randomly selected to one of the three groups
- cannot use type debugger
- can use type debugger without using additional helpers in the tool
- can use type debugger and its helpers
-->


We exercise Scala's type system using self-contained classes. They are often minimizations of real problems therefore type debugger does not benefit from them in some special way. At the end of the survey we provide a link to all all the examples and questions so that users can experiment with them more outside of this user study. 
The final section of the study conatins a number of (open) questions regarding the tool.

### How to start solving type system puzzles?

All the examples are located in the [userstudies][examplesSource] so just clone the repository using 

    git clone git://github.com/hubertp/type-debugger-tutorial.git
    cd type-debugger-tutorial/userstudies/src/main/scala/examples
    scalad subtyping/Subtyping01.scala

That will start the first example for subtyping questions.

All the examples are in the `type-debugger-tutorial/userstudies/src/main/scala/examples` directory. 

Questions (*this will be changed to the online-survey that does all the timing, gathering data etc*):

 - [Subtyping]({{BASE_PATH}}us-subtyping.html)
 - [Inference]({{BASE_PATH}}us-inference.html)
 - [Implicit search]({{BASE_PATH}}us-implicits.html)
 - [Existentials]({{BASE_PATH}}us-existentials.html)
 - Overload resolution


[examplesSource]: https://github.com/hubertp/type-debugger-tutorial/tree/master/userstudies

