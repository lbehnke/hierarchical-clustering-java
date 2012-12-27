package com.apporiented.algorithm.custering;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private String name;
	private Double distance;
	private List<Cluster> children;
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<Cluster> getChildren() {
		if (children == null)
		{
			children = new ArrayList<Cluster>();
		}
	
		return children;
	}

	public void setChildren(List<Cluster> children) {
		this.children = children;
	}

	public Cluster (String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void addChild(Cluster cluster) {
		getChildren().add(cluster);
		
	}

	public boolean contains(Cluster cluster) {
		return getChildren().contains(cluster);
	}
	
	public String toString()
	{
		return "Cluster " + name;
	}
	
	@Override
	public boolean equals(Object obj) {
		String otherName =  obj != null ? obj.toString() : "";
		return toString().equals(otherName);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
}
