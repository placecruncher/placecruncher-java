#! /bin/bash

: ${USERNAME:=test_member}
: ${PASSWORD:=w0rk13w0rk13}
: ${API_KEY:=cloud_key}
: ${API_SECRET:=cloud_secret}

unset VERBOSE
unset BASEURL

function showUsage() {
  echo
  echo "Usage: $(basename $0) [options] URL"
  echo "-b <baseurl>  The base url of the API server"
  echo "-u <username> The remote username"
  echo "-p <password> The remote password"
  echo "-v            Verbose output"
  echo "-t <token>    The authentication token to use (instead of username/password)"
  echo "-h            This message"
}

while getopts v?u:p:t:b: VAR
do
  case $VAR in
    b)
      BASEURL=${OPTARG}
      ;;
    u)
      USERNAME=${OPTARG}
      ;;
    p)
      PASSWORD=${OPTARG}
      ;;
    t)
      TOKEN=${OPTARG}
      ;;
    v)
      VERBOSE=true
      ;;
    *)
      showUsage
      exit 1
      ;;
  esac
done

shift $(( $OPTIND - 1 ))

URL=$@

# OSX
SHA_CMD="shasum -a 256"
# Linux
#SHA_CMD="sha256sum"

CURL_CMD="curl -S -s"

if [ ${VERBOSE} ]
then
  CURL_CMD+=" -v"
fi


function getApiHeaders() {
  local timestamp=$(date '+%Y%m%dT%H%M%S-0700')
  local digest=${timestamp}.${API_SECRET}
  local signature=$(echo -n ${digest}|${SHA_CMD}|awk '{print $1}')
 
  echo "-H X-API-key:${API_KEY} -H X-API-Timestamp:${timestamp} -H X-API-Signature:${signature}"
}

function postApi() {
  local url=${BASEURL}${1}

  if [ -n "${2}" ]
  then
    local data="-H Content-Type:application/json --data ${2}"
  fi
 
  local apiHeaders=$(getApiHeaders)

  if [ -n "${TOKEN}" ]
  then
    local authHeader='-H "Authentication:${TOKEN}"'
  fi

  local result=$(${CURL_CMD} -X POST ${authHeader} ${apiHeaders} ${data} ${url})

  echo ${result}
}

function getApi() {
  local url=${BASEURL}${1}

  local apiHeaders=$(getApiHeaders)

  if [ -n "${TOKEN}" ]
  then
    local authHeader="-H Authentication:${TOKEN}"
  fi

  local result=$(${CURL_CMD} -X GET ${authHeader} ${apiHeaders} ${url})

  echo ${result}
}

function getAuthenticationToken() {
  local json=$(postApi /api/private/v1/members/self/token "{\"userName\":\"${USERNAME}\",\"password\":\"${PASSWORD}\"}")
  echo $(echo ${json} | sed -n -e "s#.*\"token\":\"\(.*\)\".*#\1#p")
}

if [ -z "${TOKEN}" ]
then
  TOKEN=$(getAuthenticationToken)
fi

echo $(getApi ${URL})

exit

TOKEN="5369b292-781a-4ae6-8504-eb659a693aec"
curl -v -X GET \
-H "Authentication: ${TOKEN}" \
-H "X-API-key:${API_KEY}" \
-H "X-API-Timestamp:$(date '+%Y%m%dT%H%M%S-0700')" \
-H "X-API-Signature:${SIGNATURE}" \
http://localhost:8080/placecruncher/api/private/v1/sources/1
