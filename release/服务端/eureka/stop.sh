#!/bin/bash
PID=`ps axu | grep java | grep eureka- |grep -v grep| awk '{printf $2}'`
if [ "-$PID" = "-" ];then
    echo "no process exist!"
else
    #先优雅停机
    kill $PID
	sleep 10s
    PID=`ps axu | grep java | grep eureka- |grep -v grep| awk '{printf $2}'`
    if [ "-$PID" = "-" ];then
        echo "kill $PID process success!"
    else
        #强制停机
        kill -9 $PID
        echo "kill -9 $PID process success!"
    fi
fi
