package com.apporiented.algorithm.clustering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
    }
}
