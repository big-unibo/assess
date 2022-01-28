#!/bin/bash
set -e
set -xo

if [ -f .env ]; then
  export $(cat .env | sed 's/#.*//g' | xargs)
fi

docker-compose down
docker-compose up --build -d

./wait-for-it.sh ${ORACLE_IP}:${ORACLE_PORT} --strict --timeout=0 -- echo "ORACLE is up"

export LD_LIBRARY_PATH=!HOME!/libs/instantclient_21_1

until [ -f resources/.ready ]
do
     docker logs oracledb | tail -n 10
     sleep 10
done
echo "All databases have been imported!"
sleep 60

./gradlew --stacktrace
