#!/bin/bash
set -e
set -xo

if [ -f .env ]; then
  export $(cat .env | sed 's/#.*//g' | xargs)
fi

docker-compose down
docker-compose up --build -d

./wait-for-it.sh ${ORACLE_URL}:${ORACLE_PORT} --strict --timeout=0 -- echo "ORACLE is up"

export LD_LIBRARY_PATH=/home/mfrancia/experiments-assess/intentional/libs/instantclient_21_1

./gradlew --stacktrace