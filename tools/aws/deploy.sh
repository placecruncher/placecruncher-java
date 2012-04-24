#!/bin/bash

function usage() {
  echo "Usage: $(basename $0) [-v] [-i key] host war"
  echo
  echo "host  The hostname to deploy to"
  echo "war   The WAR file to deploy"
  echo
  echo "-v Verbose logging"
  echo "-i Private key"
  echo
}


unset key
unset verbose

while getopts i:v?h VAR
do
  case $VAR in
    v)
      verbose=true
      ;;
    i)
      key="${OPTARG}"
      ;;
    *)
      usage
      exit 1
  esac
done

shift $(( $OPTIND - 1 ))

if [ $# -lt 2 ]
then
  usage
  exit 1
fi

host=$1
war=$2
user=ec2-user

if [ ! -r "${war}" ]
then
  echo "WAR file ${war} is not readable or does not exist"
  exit 2
fi

if [ -n verbose ]; then
 echo "Deploying ${war} to ${host}"
fi

sshArgs=""
if [ -n key ]; then
  sshArgs="-i ${key}"
fi

tomcat_home=/usr/share/tomcat7/

scp ${sshArgs} ${war} ${user}@${host}:/tmp

ssh -t -t -v ${sshArgs} ${user}@${host} <<-EOF
  cleanup() {
    echo "Cleaning up tomcat files in ${tomcat_home}..."
    sudo rm -rf ${tomcat_home}/temp/*
    sudo rm -rf ${tomcat_home}/work/*
    sudo rm -rf ${tomcat_home}/webapps/*
  }

  stopTomcat() {
    sudo /etc/init.d/tomcat7 stop
  }

  startTomcat() {
    sudo /etc/init.d/tomcat7 start
  }

  deploy() {
    echo "Deploying web application..."
    sudo rm /etc/placecruncher/placecruncher.war
    sudo mv /tmp/$(basename ${war}) /etc/placecruncher/placecruncher.war
  }

  stopTomcat
  cleanup
  deploy
  startTomcat

  exit
EOF
