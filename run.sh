#!/usr/bin/env bash

# one example of run.sh script for implementing the features using python
# the contents of this script could be replaced with similar files from any major language

# I'll execute my programs, with the input directory log_input and output the files in the directory log_output
PROJ_HOME="$(builtin cd "$(dirname "$0")"; builtin pwd)"
CLASS_DIR="$PROJ_HOME/src/class"
JAVA_SRC="$(find $PROJ_HOME/src -name '*.java')"
javac -d "$CLASS_DIR" $JAVA_SRC 
java -cp "$CLASS_DIR" logprocessing.Main $PROJ_HOME/log_input/log.txt $PROJ_HOME/log_output/hosts.txt $PROJ_HOME/log_output/resources.txt $PROJ_HOME/log_output/hours.txt $PROJ_HOME/log_output/blocked.txt
