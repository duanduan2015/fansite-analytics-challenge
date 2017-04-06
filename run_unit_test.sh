#!/usr/bin/env bash

PROJ_HOME="$(builtin cd "$(dirname "$0")"; builtin pwd)"

CLASS_DIR="$PROJ_HOME/src/class"
mkdir -p "$CLASS_DIR"

JAVAC=javac 
JAVA=java

JAVA_SRC="$(find $PROJ_HOME/src/ -name '*.java')"
JUNIT_JAR="/usr/share/java/junit.jar"
HAMCREST_JAR="/usr/share/java/hamcrest/core.jar"
JUNIT_CLASS="org.junit.runner.JUnitCore"
JAVA_TEST_CLASS="test.UnitTest"

$JAVAC -d "$CLASS_DIR" -cp "$JUNIT_JAR" $JAVA_SRC 
$JAVA -cp "$CLASS_DIR":"$JUNIT_JAR":"$HAMCREST_JAR" "$JUNIT_CLASS" "$JAVA_TEST_CLASS"

