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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ClusterToIdTest {

    private Cluster<String> cluster;
    
    @Before
    public void setup() {
        Map<String,String> nameToIdMap=new HashMap<>();
        for (int i=0; i< SampleClusterData.NAMES.length;i++) {
            nameToIdMap.put(SampleClusterData.NAMES[i],String.valueOf(i));
        }


        ClusteringAlgorithm<String> alg = new DefaultClusteringAlgorithm<String>(s -> s,t->nameToIdMap.get(t));
        cluster = alg.performClustering(SampleClusterData.DISTANCES, SampleClusterData.NAMES,
                new AverageLinkageStrategy());
    }
    
    @Test
    public void testCountLeafs() throws Exception {
        int leafs = cluster.countLeafs();
        assertEquals(6, leafs);
    }
    
    @Test
    public void testGetTotalDistance() throws Exception {
        int dist = (int) cluster.getTotalDistance();
        assertEquals(10, dist);
    }
}
