#!/bin/bash
echo '#!/usr/bin/java -jar' > header.txt
#new_file="`basename $1 .jar`.jsh"
new_file=lines-of-code.jsh
cat header.txt $1 > $new_file
chmod +x $new_file
rm header.txt
echo "successfully written " $new_file
