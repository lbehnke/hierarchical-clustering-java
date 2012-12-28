package com.apporiented.algorithm.clustering.visualization;

import com.apporiented.algorithm.clustering.Cluster;

public class ClusterComponent {

	private Cluster cluster;
	private int linkX;
	private int linkY;
	private int initX;
	private int initY;
	private boolean printName;
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public int getLinkX() {
		return linkX;
	}

	public void setLinkX(int linkX) {
		this.linkX = linkX;
	}

	public int getLinkY() {
		return linkY;
	}

	public void setLinkY(int linkY) {
		this.linkY = linkY;
	}

	public int getInitX() {
		return initX;
	}

	public void setInitX(int initX) {
		this.initX = initX;
	}

	public int getInitY() {
		return initY;
	}

	public void setInitY(int initY) {
		this.initY = initY;
	}

	public boolean isPrintName() {
		return printName;
	}

	public void setPrintName(boolean printName) {
		this.printName = printName;
	}

	public ClusterComponent (Cluster cluster, boolean printName, int initX, int initY) {
		this.cluster = cluster;
		this.initX = initX;
		this.initY = initY;
		this.printName = printName;
	}
	
}
