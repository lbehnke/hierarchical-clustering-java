package com.apporiented.algorithm.custering;

public abstract class AbstractClusterAlgorithm implements ClusterAlgorithm {

	
	@Override
	public Cluster performClustering(DistanceMatrix matrix,
			int maxIterations) {

		
		
		for (int i = 0; i < maxIterations; i++) {
			
			
			matrix = aggregate(matrix);
		}
		
		return null;
	}

	private DistanceMatrix aggregate(DistanceMatrix matrix) {
		
	
		return null;
	}
	
	
	

}
