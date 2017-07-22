#!/bin/bash
set -e

#jar_file=./target/lines-of-code-1.0.0-SNAPSHOT-shaded.jar
jar_file=$1
#exec_file_name="`basename $jar_file .jar`.jsh"
#exec_file=./target/$exec_file_name
exec_file=$2

echo "jar file = $jar_file"
echo "exec file = $exec_file"

echo '#!/usr/bin/java -jar' > header.txt
cat header.txt $jar_file > $exec_file
chmod +x $exec_file
rm header.txt
echo "successfully written " $exec_file
