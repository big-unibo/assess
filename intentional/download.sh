#!/bin/bash
set -e
cd resources
curl -o foodmart-mysql.sql http://big.csr.unibo.it/projects/nosql-datasets/foodmart-mysql.sql
curl -o foodmart-mysql-schema.sql http://big.csr.unibo.it/projects/nosql-datasets/foodmart-mysql-schema.sql
curl -o COVID_WEEKLY.DMP http://big.csr.unibo.it/projects/nosql-datasets/COVID_WEEKLY.DMP
curl -o FOODMART.DMP http://big.csr.unibo.it/projects/nosql-datasets/FOODMART.DMP
curl -o FRENCHELECTRICITY.DMP http://big.csr.unibo.it/projects/nosql-datasets/FRENCHELECTRICITY.DMP
curl -o FRENCHELECTRICITYEXT.DMP http://big.csr.unibo.it/projects/nosql-datasets/FRENCHELECTRICITYEXT.DMP
curl -o SSB_FLIGHT.DMP http://big.csr.unibo.it/projects/nosql-datasets/SSB_FLIGHT.DMP
ls -las
cd -
cd libs
curl -o instantclient-basic-linux.x64-21.1.0.0.0.zip http://big.csr.unibo.it/projects/nosql-datasets/instantclient-basic-linux.x64-21.1.0.0.0.zip
unzip instantclient-basic-linux.x64-21.1.0.0.0.zip
curl -o db-migration-0.1.0.jar http://big.csr.unibo.it/projects/nosql-datasets/db-migration-0.1.0.jar
ls -las
cd -