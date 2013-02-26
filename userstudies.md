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

In order to be objective all users will be randomly selected to one of the three groups
- cannot use type debugger
- can use type debugger without using additional helpers in the tool
- can use type debugger and its helpers

Provided problems exercise Scala's type system and are presented in a form of minimal classes. They are often minimizations of real problems therefore type debugger does not benefit from them in some special way. At the end of the survey we provide a link to all all the examples and questions so that users can experiment with them more outside of this user study. 
The final section of the study conatins a number of (open) questions regarding the tool.

<!--- 
  - how long it will take
  - study is anonymous and timed

  -->