# Intentional OLAP: ASSESS

[![build](https://github.com/big-unibo/assess/actions/workflows/build.yml/badge.svg)](https://github.com/big-unibo/assess/actions/workflows/build.yml)

Implementation of the intentional operation from the TKDE ASSESS paper.

## Running the experiments

This repository
1. downloads the necessary datasets;
2. brings up a Docker container with Oracle 11g;
3. loads the datasets into Oracle;
4. runs the tests.

To do so, running the experiments requires the following software to be installed:
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
