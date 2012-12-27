package com.apporiented.algorithm.custering;


public class Linkage implements Comparable<Linkage> {

	private Cluster lCluster;
	private Cluster rCluster;
	private Double distance;
	
	public Cluster getlCluster() {
		return lCluster;
	}
	public void setlCluster(Cluster lCluster) {
		this.lCluster = lCluster;
	}
	public Cluster getrCluster() {
		return rCluster;
	}
	public void setrCluster(Cluster rCluster) {
		this.rCluster = rCluster;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Override
	public int compareTo(Linkage o) {
		int result;
		if (o == null || o.getDistance() == null ) {
			result = -1;
		}
		else if (getDistance() == null) {
			result = 1;
		}
		else {
			result = getDistance().compareTo(o.getDistance());
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += getlCluster() == null ? 0 : getlCluster().hashCode();
		result += getrCluster() == null ? 0 : getrCluster().hashCode();
		return result;
	}
	
	
	public boolean equals(Object o) {
		return o != null && o.hashCode() == hashCode();
	}
	
	public Cluster agglomerate() {
		StringBuilder sb = new StringBuilder();
		if (lCluster != null) {
			sb.append(lCluster.getName());
		}
		if (rCluster != null) {
			if (sb.length() > 0) {
				sb.append(" + ");
			}
			sb.append(rCluster.getName());
		}
		Cluster cluster = new Cluster(sb.toString());
		cluster.addChild(lCluster);
		cluster.addChild(rCluster);
		return cluster;
	}
	

	
	
	
}
