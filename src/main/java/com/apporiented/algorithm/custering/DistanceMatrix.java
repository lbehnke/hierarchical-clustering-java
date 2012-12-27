package com.apporiented.algorithm.custering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistanceMatrix {

	private List<Linkage> linkages;
	private List<Cluster> clusters;
	
	public DistanceMatrix(Double[][] initialDistanceMatrix, Cluster[] initialClusters)
	{
		if (initialDistanceMatrix == null || initialDistanceMatrix.length == 0 || initialDistanceMatrix[0].length == 0 || initialDistanceMatrix.length != initialDistanceMatrix[0].length)  {
			throw new IllegalArgumentException("Invalid distance array");
		}
		linkages = new ArrayList<Linkage>();
		clusters = new ArrayList<Cluster>();
		int len = initialDistanceMatrix.length;
		for (int col = 0; col < len; col++) {
			clusters.add(initialClusters[col]);
			for (int row = col+1; row < len; row++) {
				Linkage link = new Linkage();
				link.setDistance(initialDistanceMatrix[col][row]);
				link.setlCluster(initialClusters[col]);
				link.setrCluster(initialClusters[row]);
				if (!linkages.contains(link)) {
					linkages.add(link);
				}
				
			}
		}
	}
	
	public void agglomerate()
	{
		Collections.sort(linkages);
		if (linkages.size() > 0)
		{
			Linkage minDistLink = linkages.remove(0);
			clusters.remove(minDistLink.getrCluster());
			clusters.remove(minDistLink.getlCluster());
			
			Cluster oldClusterL = minDistLink.getlCluster();
			Cluster oldClusterR = minDistLink.getrCluster();
			Cluster newCluster = minDistLink.agglomerate();
			
			
			for (Cluster iClust : clusters) {
				Linkage link1 = findByClusters(iClust, oldClusterL);
				Linkage link2 = findByClusters(iClust, oldClusterR);
				// TODO hier weiter linkages.r
			}
			
			
			for (Linkage link : linkages) {
				
				Cluster lClust = link.getlCluster();
				Cluster rClust = link.getrCluster();
				
			
				
	
				
				for (Cluster iClust : newCluster.getChildren()) {
					Linkage l = findByClusters(link.getlCluster(), iClust);
					
				}

				
			}
			
			
		}
		
		
	}

	private Linkage findByClusters(Cluster getlCluster, Cluster iClust) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
