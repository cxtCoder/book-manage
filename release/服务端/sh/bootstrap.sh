#!/usr/bin/env bash

source /etc/profile

#================Var Begin====================
PATH_PWD=$(cd `dirname $0`;echo `pwd`)
PATH_LOCATION=/etc/init.d
JAR_NAME=$1-1.0.0-SNAPSHOT
PATH_TO_JAR=../$1/${JAR_NAME}.jar
PATH_TO_CONF=../$1/${JAR_NAME}.conf
PATH_CONF=file:../$1/conf/application.yml
PATH_LOG=./logs
START_COMMAND=""
JAVA_OPTS="-Xms128m -Xmx256m"
ADATE=`date +%Y%m%d%H%M%S`
#================Var End====================


#================Function Begin====================
checkpid(){
    pid=`ps -ef |grep $PATH_TO_JAR |grep -v grep |awk '{print $2}'`
}

service_status(){
    if [ -f ${PATH_TO_CONF} ];then
        source ${PATH_TO_CONF}
    fi   
	
    ps -ef | grep $PATH_TO_JAR | grep -v grep  >/dev/null 2>&1
    if [ $? -ne 0 ];then
        echo "$JAR_NAME is not running."
        return 1;
    else
        echo "$JAR_NAME is running."
        ps -ef | grep $PATH_TO_JAR | grep -v grep | awk '{print $2}' >/dev/null 2>&1
        return 0;
    fi

}

service_stop_force(){
    ps -ef | grep $PATH_TO_JAR | grep -v grep  >/dev/null 2>&1
    if [ $? -ne 0 ];then
        return 0;
    else
        echo "$JAR_NAME will be killed by force!"
        ps -ef | grep $PATH_TO_JAR | grep -v grep | awk '{print $2}' | xargs kill -9  >/dev/null 2>&1
        return 0;
    fi

}

service_start(){
    if [ -f ${PATH_TO_JAR} ];then
        :
    else
        echo "${PATH_TO_JAR} doesn't exist";
        return 1;
    fi    
    if [ -f ${PATH_TO_CONF} ];then
        source ${PATH_TO_CONF}
    fi    

    checkpid
    if [ -n "$pid" ]; then
        echo "$JAR_NAME is already running"
        return 1;
    else
        echo "$JAR_NAME starting ..."
        set -e
        setsid java -Dfile.encoding=utf-8 -Dspring.config.location=$PATH_CONF \
		    -Xloggc:${PATH_LOG}/${JAR_NAME}-${ADATE}-gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps \
       		-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${PATH_LOG} \
             ${JAVA_OPTS} -jar -Dspring.config.location=$PATH_CONF $PATH_TO_JAR $START_COMMAND 2>&1 > ${PATH_LOG}/${JAR_NAME}-${ADATE}.out &
        if [ $? -ne 0 ];then
            echo "$JAR_NAME started  failed"
            exit 1
        else
            echo "$JAR_NAME started ..."
        fi
        set +e
        return 0;
    fi
}

service_stop(){
    checkpid
    if [ -n "$pid" ]; then
        echo "$JAR_NAME stoping ..."
        ps -ef | grep $PATH_TO_JAR | grep -v grep | awk '{print $2}' | xargs kill -15  >/dev/null 2>&1
        echo "$JAR_NAME stopped ..."
        return 0;
    else
        echo "$JAR_NAME is not running ..."
        return 1;
    fi
}

service_tag(){
    echo "----------------------------------------------------------------------------------------------------------"
}

service_info(){
    service_tag
    echo "info-use          bootstrap ${JAR_NAME} start|stop|status|restart"
    echo "info-jar          $PATH_TO_JAR"
    echo "info-log          ${PATH_LOG}/${JAR_NAME}-yyyymmddHHMMSS.out"
    echo "info-gc-log       ${PATH_LOG}/${JAR_NAME}-yyyymmddHHMMSS-gc.log"
    echo "info-heapdump-log ${PATH_LOG}"
    echo "info-java-opts    ${JAVA_OPTS}"
    service_tag
}
#================Function End====================

if [ ! -d "./logs" ];then
  mkdir ./logs
fi

#==================Entrance Begin===============
# 接收命令开始
case "$2" in
    "status")
        service_tag
        service_status
        service_info
        ;;
    "start")
        service_status >/dev/null 2>&1
        service_start
        service_tag
        service_status
        ;;
    "stop")
        service_status >/dev/null 2>&1
        service_tag
        service_stop
        ;;
    "forcestop")
        service_status >/dev/null 2>&1
        service_tag
        service_stop_force
        ;;
    "restart")
        service_status >/dev/null 2>&1
        service_stop
        service_start
        service_tag
        service_status
        ;;
    *)
    apps=`ls apps | grep .jar | grep -v grep | sed -e ':a;N;$!ba;s/.jar\n/|/g' -e 's/.jar//g'`
	echo "Usage: .\bootstrap.sh ${apps} status|start|stop|forcestop|restart" ;;
esac
#==================Entrance End===============
