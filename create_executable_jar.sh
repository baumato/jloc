#!/bin/bash
set -e

echo arguments=\"$@\"

jar_file=$1
exec_file=$2

echo "jar file = $jar_file"
echo "exec file = $exec_file"

echo '#!/usr/bin/java -jar' > header.txt
cat header.txt $jar_file > $exec_file
chmod +x $exec_file
rm header.txt
echo "successfully written " $exec_file
