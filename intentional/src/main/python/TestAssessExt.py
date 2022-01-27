import pandas as pd
import unittest
import math
# sys.path.append('src/main/python/')
# sys.path.append('../../main/python/')
from assess import *
from assess_ext import *


class TestAssess(unittest.TestCase):

    def test_all(self):
        byclause = ["category"]
        measure = "quantity"
        k = 3

        X = pd.DataFrame([["a", 1, 2, 3, 4, 5], ["b", 11, 12, 13, 14, 15], ["c", 21, 22, 23, 24, 25]])
        X.columns = ["category", "quantity", "RUSSIA", "RUSSIA_population", "FRANCE", "FRANCE_population"]
        res = compute_auto_benchmarks(X, k, measure, byclause)
        self.assertTrue(len(res) == 2)

        res = compute_auto_using(res[0][0], k, measure, byclause)
        self.assertTrue(len(res) == 3)

        res = compute_auto_labels(res[0][0], k, measure, byclause)
        self.assertTrue(len(res) == 3)


if __name__ == '__main__':
    unittest.main()
