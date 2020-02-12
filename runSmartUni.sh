#!/usr/bin/env bash
echo "Grafan and InfluxDB needed!!"

artifacts="/home/dinci11/SmartUniversity/dds-samrtuniversity/out/artifacts"

for f in $(find /home/dinci11/SmartUniversity/dds-samrtuniversity/out/artifacts -name '*.jar')
do
    echo $f
    gnome-terminal --window-with-profile=stayopen -- java -jar $f    
done
