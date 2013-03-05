---
layout: page
title: "Tool setup"
tagline: "Getting latest version"
---
{% include JB/setup %}

### Setup (short version)
Zip contains all the necessary third-party jars (including all scala-\*.jars) so the archive is pretty heavy (< 30MB). Instructions on how to get it on Linux are:


    wget http://lampwww.epfl.ch/~plocinic/type-debugger-binaries/scalad-dist-snapshot.zip
    unzip scalad-dist-snapshot.zip
    chmod a+x bin/*
    ./bin/scalad files*


### Setup (long version)

The sources for the prototype are available at [GitHub][scaladsource] (needs update). If you want to build type debugger from scratch on your own you will have to build a specific version of the compiler as well (details available in the repository). For convience we provide ready-to-use binaries at our [snapshot page][download]. The current version of the compiler used is {{ site.scala_compiler.version }}.

After downloading the tarball from the [download page][download] you will have a standard set of command line tools available for compiling and running Scala programs. Apart from that there exists an additional shell script **bin/scalad** which can start our tool. **scalad** is just a wrapper around the standard **scalac** script which makes sure that correct jars are on the classpath. The script accepts the same set of options as the normal **scalac**. In other words you can treat **scalad** as a normal Scala compiler frontend with type debugging support (type debugger will never generate bytecode).

[download]: {{ site.sources.download }}/scalad-dist-snapshot.zip
[scaladsource]: https://github.com/hubertp/prefuse-type-debugger "Type debugger prototype"