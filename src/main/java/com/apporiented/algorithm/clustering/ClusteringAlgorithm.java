package com.apporiented.algorithm.clustering;

public interface ClusteringAlgorithm {

	public Cluster performClustering(double[][] distances,
	        String[] clusterNames, LinkageStrategy linkageStrategy);

}
