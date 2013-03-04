---
layout: page
title: "Questions: Overload resolution (case study)"
---
{% include JB/setup %}

### OverloadResolution (source at *inference/OverloadResolution.scala*)

Overload resolution is one of the most recognizable features of object oriented programming. Unfortunately it can be also problematic to use and understand therefore ending being discouraged among programmers.
We hope that the little instrumentation that we have for type debugger in that area should be helpful to understand the basic problems.

#### test01

 - For application **A** which of the three alternatives is valid? If there is more than one, why do we not get the ambiguity?

 - For application **B** which of the three alternatives is valid? If there is more than one, why do we not get the ambiguity?

#### test02
 - Are both `foo` alternatives applicable in the application **A**? If yes, why do we not get an ambiguity?

 - Are both `foo` alternatives applicable in the application **B**? If yes, why do we not get an ambiguity?

#### test03
 - Are both `square` alternatives applicable in the application **A**? If yes, why do we not get an ambiguity?

 - Are both `square` alternatives applicable in the application **B**? If yes, why do we not get an ambiguity?

#### test04
In this example the signatures of `foo` and `bar` are very identical yet defined in opposite order in the base class and the subclass. Questions ask how does this affect overload resolution.

 - Why application **A** report an ambiguity whereas application **C** with the same argument does not?
 - Why do we have not the ambiguity problem with applications **B** and **D**?

#### test05
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

 - Are both alternatives applicable for application **B**? If yes, why is there no ambiguity?

#### test06
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

 - Are both alternatives applicable for application **B**? If yes, why is there no ambiguity?

#### test07
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

 - Are both alternatives applicable for application **B**? If yes, why is there no ambiguity?

#### test08
 - Are both alternatives applicable for application **A**? If yes, why is there no ambiguity?

 - Why is there an ambiguity in application **B** but there was none in a similar example in **test07** (also application **B**)?

#### test09
 - We have an ambiguity in the application **A**. Is it possible to call method **2** with the current definition of classes `Base` and `SubBase`? If yes, how?

#### test10
 - Are alternatives **1, 2 and 3** applicable in the application **A**? If yes, why do we not get an ambiguity?
 - Why ambiguity cannot be resolved in a similar scenario for application **C**? Can method **5** be ever called?

#### test11
 - Why are alternatives **1 and 2** applicable in the application **A** at all? Why ambiguity cannot be resolved ?
 - Are both alternatives **1 and 2** applicable in the application of **B**? If yes, why do we not get the same ambiguity problem as above?

 - Which of the alternatives **3, 4 and 5** are applicable in the application **C** and why? If there is more than one, why do we not have an ambiguity?

 - Which of the alternatives **3, 4 and 5** are applicable in the application **D** and why? If there is more than one, why do we not have an ambiguity?
 

### Overload resolution and implicits (source at *inference/OverloadAndImplicits.scala*)
 - Which of the alternatives **1 and 2** is applicable for the argument `a` in application **A**? Are implicits `b2Int`, `a2Int` and/or `foo2Base` involved in the verification of the alternative process?  Explain their role (if any) in typechecking the application.

 - Which of the alternatives **1 and 2** is applicable for the argument `a` in application **B**? Are implicits `b2Int`, `a2Int` and/or `foo2Base` involved in the verification of the alternative process? Explain their role (if any) in typechecking the application.

 - Which of the alternatives **1 and 2** is applicable for the argument `a` in application **B**? Are implicits `b2Int`, `a2Int` and/or `foo2Base` involved in the verification of the alternative process? Explain their role (if any) in typechecking the application.

### Overload resolution and implicits 2 (source at *inference/OverloadAndImplicits2.scala*)
Notice that in this example the only difference between `test01` and `test02` is that the former assigns temporarily its argument before continuing with the application.

Ambiguity arises for application involing method `aFunc` in application **A**. Also argument `p` of type `Int` is clearly not immediately applicable as an argument.
 - Which of the implicit conversions `conv*` would convert the argument `p` so that it is suitable for alternative **1**? 
 - Which of the implicit conversions `conv*` would convert the argumnet `p` so that it is suitale for alternative **2**?

Application **B** is very similar.
 - Which of the implicit conversions `conv*` would convert the argument `p` so that it is suitable for alternative **1**? 
 - Which of the implicit conversions `conv*` would convert the argumnet `p` so that it is suitale for alternative **2**?
 - Are they same as for application **A**? If yes, why do we not get ambiguity? If no, why are they different?