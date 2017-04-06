#!/bin/sh

if [ "$#" != 2 ]; then
    echo "Usage: $0 <log_file> <N>"
    exit 1
fi

cut "$1" -d' ' -f1 | python3 count.py | sort -nr | head -"$2"
