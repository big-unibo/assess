#!/bin/bash
set -e
cd resources
curl -k -o foodmart-mysql.sql https://big.csr.unibo.it/projects/nosql-datasets/foodmart-mysql.sql
curl -k -o foodmart-mysql-schema.sql https://big.csr.unibo.it/projects/nosql-datasets/foodmart-mysql-schema.sql
curl -k -o COVID_WEEKLY.DMP https://big.csr.unibo.it/projects/nosql-datasets/COVID_WEEKLY.DMP
curl -k -o FOODMART.DMP https://big.csr.unibo.it/projects/nosql-datasets/FOODMART.DMP
curl -k -o FRENCHELECTRICITY.DMP https://big.csr.unibo.it/projects/nosql-datasets/FRENCHELECTRICITY.DMP
curl -k -o FRENCHELECTRICITYEXT.DMP https://big.csr.unibo.it/projects/nosql-datasets/FRENCHELECTRICITYEXT.DMP
curl -k -o SSB_FLIGHT.DMP https://big.csr.unibo.it/projects/nosql-datasets/SSB_FLIGHT.DMP
ls -las
cd -
cd libs
curl -k -o instantclient-basic-linux.x64-21.1.0.0.0.zip https://big.csr.unibo.it/projects/nosql-datasets/instantclient-basic-linux.x64-21.1.0.0.0.zip
unzip instantclient-basic-linux.x64-21.1.0.0.0.zip
curl -k -o db-migration-0.1.0.jar https://big.csr.unibo.it/projects/nosql-datasets/db-migration-0.1.0.jar
ls -las
cd -
