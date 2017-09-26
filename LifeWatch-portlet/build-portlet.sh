#!/bin/bash

if [ ! -d lib ]; then
    mkdir -p lib
fi
if [ ! -f lib/gson-2.6.2.jar ]; then
    wget http://central.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar -P lib/
fi

blade gw clean jar
