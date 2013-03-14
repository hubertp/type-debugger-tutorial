---
layout: page
title: "Questions: Overloading resolution (case study)"
---
{% include JB/setup %}

### Overloading Resolution (source at *inference/OverloadResolution.scala*)

If you are running out of time, feel free to skip this case study on overloading resolution. There is still only section left about Implicit Search.

Overload resolution is one of the most recognizable features of object oriented programming. Unfortunately it can be also problematic to use and understand therefore ending being discouraged among programmers. Although type debugger has only little support for showing overloading resolution we hope that it is already enough to answer common questions like below.


#### test01
In this example the signatures of `foo` and `bar` are identical yet they are defined in the opposite order in the base class and the subclass.

 - Application **A** reports an ambiguity. Application **B** with the same argument does not. Explain the difference in terms of overloading resultion.

#### test02
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

#### test03
 - Are alternatives **1, 2 and 3** applicable in the application **A**? If yes, why do we not get an ambiguity?
 - Why ambiguity cannot be resolved in a similar scenario for application **B** with alternatives **4 and 5**?

#### test04
You can see a number of implicit conversions which could potentially help in the typechecking the applications **A** and **B**. Without implicits none of the applications in this example would clearly be invalid to to the types of the arguments.
However we still get typing errors related to overloaded methods in applications `c.foo(a)` and `c.foo(b)`. 

Your answers to the following questions should explain why implicits didn't help.

 - Why alternative **x** is not valid in the application **A**?
 - Why alternative **y** is not valid in the application **A**?

 - Why alternative **x** is not valid in the application **B**?
 - Why alternative **y** is not valid in the application **B**?

##### test05
Notice that in this example the only difference between `test01` and `test02` methods is that the former assigns temporarily its argument before continuing with the application.

Ambiguity arises for application involving method `aFunc` in application **A**. Also argument `p` of type `Int` is clearly not immediately applicable as an argument.
Application **B** is very similar.
 
 - Why is the typechecker able to infer the correct `aFunc` alternative in the case of application **B** but reports a typing error in the case of application **A**?