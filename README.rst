============
time-compile
============

Summarize a timeline written in text.

License
=======

The version 3 of the GNU General Public License, or (at your option)
any later version, applies to this source code.

Building
========

Prerequisite: install the [pro](https://github.com/forax/pro) build
tool.

To build::

  $ pro

Releasing
=========

To release::

  $ version=0.3
  $ git tag -am "v$version" "v$version"
  $ ./release "$version"
