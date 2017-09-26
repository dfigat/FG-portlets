#!/bin/bash

if [ ! -d lib ]; then
    mkdir -p lib
fi
if [ ! -f lib/gson-2.6.2.jar ]; then
    wget http://central.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar -P lib/
fi

blade gw clean jar
scp build/libs/*.portlet-*.jar futuregateway@fgw01.ncg.ingrid.pt:~/FutureGateway/deploy
#scp ../portletConfigFiles/*json futuregateway@fgw01.ncg.ingrid.pt:~/FutureGateway/portletConfigFiles
#scp -P 10060 build/libs/*.portlet-*.jar ubuntu@149.156.11.4:/home/ubuntu/liferay-ce-portal-7.0-ga3/deploy
