---
layout: page
---
{% include JB/setup %}

## Purpose ## {#Purpose}

Scala is a multi-paradigm language integrating functional and object-oriented programming. Scala is statically typed and supports a variation of local type inference. Therefore it can provide compile-time guarantees but at the same users have to deal with a non-trivial type system, sometimes counter-intuitive types inferred by the compiler or confusing error messages. Apart from a couple of diagnostic techniques there is currently no way for the users to understand the typechecking that runs as part of the compilation. The purpose of **scalad** is to provide the programmers with a tool that allows them to explore the typechecking process, understand decisions that are made at every stage, see how type arguments are inferred, where and why implicit values were picked or what lead to an error.

## Setup ##
To start quickly we provide latest binaries for type debugger as described in the [setup page](toolsetup.html).

## Tutorial ##
The tutorial for the latest version of type debugger is available [here](tutorial.html).

## User studies ##
We will be running [user studies](userstudies.html) that cover the basics and more advanced of the possible usages of the type debugger prototype
