package com.apporiented.algorithm.clustering;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apporiented.algorithm.custering.AverageLinkageStrategy;
import com.apporiented.algorithm.custering.Cluster;
import com.apporiented.algorithm.custering.ClusteringAlgorithm;
import com.apporiented.algorithm.custering.DefaultClusteringAlgorithm;

public class DefaultClusteringAlgorithmTest {

	private double[][] distances;
	private String[] names;

	@Before
	public void setup() {
		distances = new double[][] { { 0, 1, 9, 7, 11, 14 },
		        { 1, 0, 4, 3, 8, 10 }, { 9, 4, 0, 9, 2, 8 },
		        { 7, 3, 9, 0, 6, 13 }, { 11, 8, 2, 6, 0, 10 },
		        { 14, 10, 8, 13, 10, 0 } };
		names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };

	}

	@Test
	public void testClusteringAvgLink() {
		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster c = alg.performClustering(distances, names,
		        new AverageLinkageStrategy());
		Assert.assertNotNull(c);
	}

}
