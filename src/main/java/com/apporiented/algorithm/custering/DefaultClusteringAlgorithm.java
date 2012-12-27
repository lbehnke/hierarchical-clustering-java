package com.apporiented.algorithm.custering;

import java.util.ArrayList;
import java.util.List;

public class DefaultClusteringAlgorithm implements ClusteringAlgorithm {


	@Override
	public Cluster performClustering(double[][] distances, String[] clusterNames,
			LinkageStrategy linkageStrategy) {
		
		/* Argument checks */
		if (distances == null || distances.length == 0 || distances[0].length != distances.length ) {
			throw new IllegalArgumentException("Invalid distance matrix");
		}
		if (distances.length != clusterNames.length) {
			throw new IllegalArgumentException("Invalid cluster name array");
		}
		if (linkageStrategy == null) {
			throw new IllegalArgumentException("Undefined linkage strategy");
		}

		/* Setup model */
		List<Cluster> clusters = createClusters(clusterNames);
		List<ClusterPair> linkages = createLinkages(distances, clusters);
		
		/* Process */
		HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
		while (!builder.isTreeComplete()) {
			builder.agglomerate(linkageStrategy);
		}
		
		return builder.getRootCluster();
	}

	private List<ClusterPair> createLinkages(double[][] distances, List<Cluster> clusters) {
		List<ClusterPair> linkages = new ArrayList<ClusterPair>();
		for (int col = 0; col < clusters.size(); col++) {
			for (int row = col+1; row < clusters.size(); row++) {
				ClusterPair link = new ClusterPair();
				link.setLinkageDistance(distances[col][row]);
				link.setlCluster(clusters.get(col));
				link.setrCluster(clusters.get(row));
				linkages.add(link);
			}
		}
		return linkages;
	}

	private List<Cluster> createClusters(String[] clusterNames) {
		List<Cluster> clusters = new ArrayList<Cluster>();
		for (int col = 0; col < clusterNames.length; col++) {
			Cluster cluster = new Cluster(clusterNames[col]);
			clusters.add(cluster);
		}
		return clusters;
	}
	
	
	

}
