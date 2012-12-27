package com.apporiented.algorithm.custering;

public interface ClusterAlgorithm {
	
	public Cluster performClustering(DistanceMatrix initialMatrix, int maxIterations);
}
