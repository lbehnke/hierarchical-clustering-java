package com.apporiented.algorithm.custering;

public interface ClusteringAlgorithm {

	public Cluster performClustering(double[][] distances,
	        String[] clusterNames, LinkageStrategy linkageStrategy);

}
