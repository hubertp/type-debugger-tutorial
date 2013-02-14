---
layout: page
title: "Tool setup"
tagline: "Getting latest version"
---
{% include JB/setup %}

The sources for the prototype are available at [GitHub][scaladsource]. If you want to build type debugger from scratch on your own you will have to build a specific version of the compiler as well (details available in the repository). For convience we provide ready-to-use binaries at our [snapshot page][download]. The current version of the compiler used is {{ site.scala_compiler.version }}.

After downloading the tarball from the [download page][download] you will have a standard set of command line tools available for compiling and running Scala programs. However an additional shell script exists `scalad` which can start our tool. `scalad` is just a wrapper around the standard `scalac` script which makes sure that correct jars are on the classpath. `scalad` accepts the same set of options that the normal `scalac` does. In other words you can treat `scalad` as a normal Scala compiler frontend with type debugging support (type debugger does not generate bytecode).

### Stop talking, I want to run the thing!

    scalad path/to/my/favourite/Error.scala

[download]: {{ site.sources.download }}
[scaladsource]: https://github.com/hubertp/prefuse-type-debugger "Type debugger prototype"