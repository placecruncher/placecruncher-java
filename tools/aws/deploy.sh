#!/bin/bash

function usage() {
  echo "Usage: $(basename $0) [-v] [-i key] [-c context] host war"
  echo
  echo "host  The hostname to deploy to"
  echo "war   The WAR file to deploy"
  echo
  echo "-v Verbose logging"
  echo "-i Private key"
  echo "-c Web application context (default: placecruncher)"
  echo
}


unset key
unset verbose
unset context

while getopts i:c:v?h VAR
do
  case $VAR in
    c)
      context="${OPTARG}"
      ;;
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

: ${context:="placecruncher"}
host=$1
war=$2

if [ ! -r "${war}" ]
then
  echo "WAR file ${war} is not readable or does not exist"
  exit 2
fi

if [ -n verbose ]; then
 echo "Deploying ${war} to ${host} using context ${context}"
fi

sshArgs=""
if [ -n key ]; then
  sshArgs="-i ${key}"
fi

ssh ${sshArgs} $host <<EOF
  cleanup() {
  }

  stopTomcat() {
    sudo /etc/init.d/tomcat7 stop
  }

  startTomcat() {
    sudo /etc/init.d/tomcat7 start
  }

  deploy() {
    sudo 
  }
    startJboss() {
        cd $JBOSS_SERVER/bin
        ./jbossctl.sh start
        return 0;
    }
export JBOSS_HOME
export JBOSS_SERVER=$JBOSS_HOME/server/default
END
return 0;
}
