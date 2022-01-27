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
cd -