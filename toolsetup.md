---
layout: page
title: "Tool setup"
tagline: "Getting latest version"
---
{% include JB/setup %}

The sources for the prototype are available at [GitHub][scaladsource]. If you want to build type debugger from scratch on your own you will have to build a specific version of the compiler as well (details available in the repository). For convience we provide ready-to-use binaries at our [snapshot page][download]. The current version of the compiler used is {{ site.scala_compiler.version }}.

After downloading the tarball from the [download page][download] you will have a standard set of command line tools available for compiling and running Scala programs. Apart from that there exists an additional shell script **bin/scalad** which can start our tool. **scalad** is just a wrapper around the standard **scalac** script which makes sure that correct jars are on the classpath. The script accepts the same set of options as the normal **scalac**. In other words you can treat **scalad** as a normal Scala compiler frontend with type debugging support (type debugger will never generate bytecode).

### How to use it?


    scalad path/to/my/favourite/Error.scala

[download]: {{ site.sources.download }}
[scaladsource]: https://github.com/hubertp/prefuse-type-debugger "Type debugger prototype"