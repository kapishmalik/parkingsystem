#!/bin/bash
mvn clean install > mvn-build-output.txt
if [ $# -gt 0 ]
then
 java -jar target/parkingsystem-0.1.jar $1
else
 printf "Please provide input file path"
fi