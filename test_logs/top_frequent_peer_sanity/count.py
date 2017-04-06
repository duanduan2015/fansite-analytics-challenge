#!/usr/bin/env python3

import sys

count = {}

for rawLine in sys.stdin:
    line = rawLine.strip()
    if line in count:
        count[line] += 1
    else:
        count[line] = 1
    
for k in count:
    print("%d\t%s" % (count[k], k))

