#!/usr/bin/env bash

PROJ_HOME="$(builtin cd "$(dirname "$0")"; builtin pwd)"

CLASS_DIR="$PROJ_HOME/src/class"
mkdir -p "$CLASS_DIR"

JAVAC=javac 
JAVA=java

JAVA_SRC="$(find $PROJ_HOME/src/tools $PROJ_HOME/src/logprocessing -name '*.java')"
$JAVAC -d "$CLASS_DIR" $JAVA_SRC 
$JAVA -cp "$CLASS_DIR" logprocessing.Main $PROJ_HOME/log_input/log.txt $PROJ_HOME/log_output/hosts.txt $PROJ_HOME/log_output/resources.txt $PROJ_HOME/log_output/hours.txt $PROJ_HOME/log_output/blocked.txt
