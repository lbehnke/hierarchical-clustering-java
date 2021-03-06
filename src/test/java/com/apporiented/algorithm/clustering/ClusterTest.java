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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClusterTest {

    private Cluster cluster;
    
    @Before
    public void setup() {
        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        cluster = alg.performClustering(SampleClusterData.DISTANCES, SampleClusterData.NAMES,
                new AverageLinkageStrategy());
    }
    
    @Test
    public void testCountLeafs() throws Exception {
        int leafs = cluster.countLeafs();
        assertEquals(6, leafs);
        assertEquals("clstr#5", cluster.getName());
    }
    
    @Test
    public void testGetTotalDistance() throws Exception {
        int dist = (int) cluster.getTotalDistance();
        assertEquals(10, dist);
        assertEquals("clstr#5", cluster.getName());
    }
}
