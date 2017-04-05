#!/usr/bin/env bash

# one example of run.sh script for implementing the features using python
# the contents of this script could be replaced with similar files from any major language

# I'll execute my programs, with the input directory log_input and output the files in the directory log_output
PROJ_HOME="$(builtin cd "$(dirname "$0")"; builtin pwd)"

CLASS_DIR="$PROJ_HOME/src/class"
mkdir -p "$CLASS_DIR"


JAVAC=javac 
JAVA=java

############### for JUnit test ####################
#JAVA_SRC="$(find $PROJ_HOME/src -name '*.java')"
#JUNIT_JAR="/usr/share/java/junit.jar"
#HAMCREST_JAR="/usr/share/java/hamcrest/core.jar"
#JUNIT_CLASS="org.junit.runner.JUnitCore"
#JAVA_TEST_CLASS="test.UnitTest"
#$JAVAC -d "$CLASS_DIR" -cp "$JUNIT_JAR" $JAVA_SRC 
#$JAVA -cp "$CLASS_DIR":"$JUNIT_JAR":"$HAMCREST_JAR" "$JUNIT_CLASS" "$JAVA_TEST_CLASS"
###################################################

############## normal run.sh ######################
JAVA_SRC="$(find $PROJ_HOME/src/tools $PROJ_HOME/src/logprocessing -name '*.java')"
$JAVAC -d "$CLASS_DIR" $JAVA_SRC 
$JAVA -cp "$CLASS_DIR" logprocessing.Main $PROJ_HOME/log_input/log.txt $PROJ_HOME/log_output/hosts.txt $PROJ_HOME/log_output/resources.txt $PROJ_HOME/log_output/hours.txt $PROJ_HOME/log_output/blocked.txt
###################################################
