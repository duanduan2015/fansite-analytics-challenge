#!/usr/bin/env python3

import sys
import re

count = {}
nLinesMatched = 0

for rawLine in sys.stdin:
    m = re.match(r'^[^"]+"\S+ (\S+)(?:\s+.*)?" \d+ ([-\d]+)\s*$', rawLine)
    if m:
        path = m.group(1)
        length = 0 if m.group(2) == '-' else int(m.group(2))
        nLinesMatched += 1

        if path in count:
            count[path] += length
        else:
            count[path] = length
    
for k in count:
    print("%d\t%s" % (count[k], k))

