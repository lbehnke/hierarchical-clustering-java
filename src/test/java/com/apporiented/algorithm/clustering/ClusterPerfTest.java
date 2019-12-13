/*******************************************************************************
 * Copyright 2013 Lars Behnke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.apporiented.algorithm.clustering;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClusterPerfTest {

    Cluster randomCluster(int n) {
        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();

        return alg.performClustering(randomDataDist(n), randomDataNames(n),
                new AverageLinkageStrategy());
    }

    private double[][] randomDataDist(int n) {
        Random rnd = new Random();
        double[][] mat = new double[n][n];
        for (int i = 0; i < n; i++) {
            mat[i][i] = 0;
            for (int j = i + 1; j < n; j++) {
                double r = Math.floor(rnd.nextDouble() * 100) * 0.1;
                mat[i][j] = r;
                mat[j][i] = r;
            }
        }

        return mat;
    }

    private String[] randomDataNames(int n) {
        String[] ret = new String[n];
        for (int i = 0; i < n; i++) {
            ret[i] = "" + i;
        }
        return ret;
    }

    @Test
    public void testRandomDataDist() throws Exception {
        double[][] dist = randomDataDist(4);
        assertEquals(dist.length, 4);
    }

    @Test
    public void testRandomDataNames() throws Exception {
        String[] names = randomDataNames(4);
        assertEquals(names.length, 4);
        String[] exp = {"0", "1", "2", "3"};
        assertArrayEquals(names, exp);
    }

    private Long timeN(int n) {
        Long t0 = System.currentTimeMillis();
        Cluster cluster = randomCluster(n);
        return System.currentTimeMillis() - t0;
    }

    @Test
    public void testn() throws Exception {
        for (int n = 1; n <= 1024; n = n * 2) {
            Long t = timeN(n);
            System.out.println(String.format("%3d nodes -> %5d ms", n, t));
        }
    }
}
