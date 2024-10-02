# Intentional OLAP: ASSESS

[![build](https://github.com/big-unibo/assess/actions/workflows/build.yml/badge.svg)](https://github.com/big-unibo/assess/actions/workflows/build.yml)

## Research papers

Please refer/cite to the following research paper:

- Matteo Francia, Matteo Golfarelli, Patrick Marcel, Stefano Rizzi, and Panos Vassiliadis. "Suggesting assess queries for interactive analysis of multidimensional data." **IEEE Transactions on Knowledge and Data Engineering** (2022). DOI: https://doi.org/10.1109/TKDE.2022.3171516

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

```bash
cd intentional
chmod +x *.sh
./init.sh
./build.sh
./download.sh
./start.sh
./stop.sh
```
