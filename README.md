# Intentional OLAP: ASSESS

[![build](https://github.com/big-unibo/assess/actions/workflows/build.yml/badge.svg)](https://github.com/big-unibo/assess/actions/workflows/build.yml)

Implementation of the intentional operation from the TKDE ASSESS paper.

## Running the experiments

This repository allows the user to:
1. download the necessary datasets;
2. bring up a Docker container with Oracle 11g;
3. load the datasets into Oracle;
4. run the tests.

Running the experiments requires the following software to be installed:
- Docker
- Java 14
- Python 3.6.9

Once the software is installed, execute the following code to run the tests.

    cd intentional
    chmod +x *.sh
    ./init.sh
    ./build.sh
    ./download.sh
    ./start.sh
    ./stop.sh
