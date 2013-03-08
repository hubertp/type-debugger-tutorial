---
layout: page
title: "Questions: Overload resolution (case study)"
---
{% include JB/setup %}

### OverloadResolution (source at *inference/OverloadResolution.scala*)

Overload resolution is one of the most recognizable features of object oriented programming. Unfortunately it can be also problematic to use and understand therefore ending being discouraged among programmers. Although type debugger has only little support for showing overloading resolution we hope that it is already enough to answer common questions like below.


#### test01
In this example the signatures of `foo` and `bar` are identical yet defined in the opposite order in the base class and the subclass.

 - Why application **A** report an ambiguity whereas application **C** with the same argument does not?

#### test02
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

#### test03
 - Are alternatives **1, 2 and 3** applicable in the application **A**? If yes, why do we not get an ambiguity?
 - Why ambiguity cannot be resolved in a similar scenario for application **C** with alternatives **4 and 5**? Can method **5** be ever called?

#### test04
You can see a number of implicit conversions which could potentially help in the typechecking. However one gets ambiguity errors for expressions `c.foo(a)` and `c.foo(b)`.
Your answers to the following questions should explain why implicits didn't help.

 - Why alternative **x** is not valid in the application **A**?
 - Why alternative **y** is not valid in the application **A**?

 - Why alternative **x** is not valid in the application **B**?
 - Why alternative **y** is not valid in the application **B**?

##### test05
Notice that in this example the only difference between `test01` and `test02` is that the former assigns temporarily its argument before continuing with the application.

Ambiguity arises for application involing method `aFunc` in application **A**. Also argument `p` of type `Int` is clearly not immediately applicable as an argument.
 - Which of the implicit conversions `conv*` would convert the argument `p` so that it is suitable for alternative **1** in the application `aFunc(p)`? 
 - Which of the implicit conversions `conv*` would convert the argumnet `p` so that it is suitable for alternative **2** in the application `aFunc(p)`?

Application **B** is very similar.
 - Why is the typechecker able to infer the correct `aFunc` alternative in the case of **B** expression?