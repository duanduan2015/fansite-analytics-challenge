#!/bin/sh

if [ "$#" != 2 ]; then
    echo "Usage: $0 <log_file> <N>"
    exit 1
fi

python3 count.py < "$1" | sort -nrk1 | head -"$2"
