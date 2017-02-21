============
time-compile
============

Summarize a timeline written in text.

Building
========

To build::

  $ bazel build //...

To test::

  $ bazel test //tests:all

To run::

  $ ./bazel-bin/time-compile-cli -h
  time-compile [OPTION...] TIMEFILE
   Options:
     -h, --help                       Show this help screen.
     -m, --maps=MAPFILE[,MAPFILE...]  Apply transformations to the tags.


Development
===========

If you use Eclipse, there is a e2b plugin available, but it does not
do much.

You'll in any case need to tell Eclipse where to find the jopts jar.

Also, if you have bazel errors about file target/classes/BUILD, tell
Eclipse to ignore the BUILD files instead of copying them into the
target folder.
