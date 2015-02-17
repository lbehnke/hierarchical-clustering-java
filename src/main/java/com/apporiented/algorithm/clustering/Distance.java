package com.apporiented.algorithm.clustering;


/**
 * Created by lars on 16.02.15.
 */
public class Distance implements Comparable<Distance>, Cloneable {

    private Double distance;
	private Double weight;

    public Distance() {
		this(0.0);
    }

    public Distance(Double distance) {
		this(distance, 1.0);
    }

    public Distance(Double distance, Double weight) {
        this.distance = distance;
        this.weight = weight;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isNaN() {
        return distance == null || distance.isNaN();
    }

    @Override
    public int compareTo(Distance distance) {
        return distance == null ? 1 : getDistance().compareTo(distance.getDistance());
    }

	@Override
	public String toString() {
		return String.format("distance : %.2f, weight : %.2f", distance, weight);
	}
}
