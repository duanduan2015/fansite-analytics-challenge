## How to run

Tested on: Fedora 25 64bit with java, javac, and junit package available.

Script `run.sh` compiles and runs the program, taking `log_input` and
`log_output` as input folder and output folder, respectively.

Script `run_unit_test.sh` compiles and tests the program using JUnit.  In this
script, there are many variables regarding JUnit needs to be configured
correctly for `java` and `javac` to know how to run with JUnit. They are:
`JUNIT_JAR`, `HAMCREST_JAR`, and `JUNIT_CLASS`. Please reconfigure them if the
script doesn't work as expected.

Script `insight_testsuite/run_tests.sh` (not changed) performs high-level tests
using one given test and 13 customized tests.

## Design

* Online execution. This design treats the input as an infinite stream. Only
  necessary information resides in memory. Report collection is designed to be
  lightweight. User doesn't have to get report after the whole execution. For
  feature 1, 2, and 3, report collection time complexity is O(k) (k = 10 in the
  cases). For feature 4, blocking actions are issued in O(1) in a real-time
  manner.

* Compact memory design. When building maps using host name or resource path as
  keys, instead of using hash map or other data structures, a `PrefixMap` (Trie)
  is designed to reuse upper level of domain or directory names.

* Extensible. One analyser is designed for each feature. They implement a common
  interface `Analyser`, which helps further extend the program with more
  functionalities.
