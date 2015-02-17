package com.apporiented.algorithm.clustering;

import java.util.Comparator;

/**
 * Created by lars on 16.02.15.
 */
public class Distance implements Comparable<Distance>, Cloneable {

    private Double distance;
	private Double weight = 1.0;

    public Distance() {
        this(null, null);
    }

    public Distance(Distance distance) {
        this(distance.getDistance(), distance.getWeight());
    }

    public Distance(Double distance) {
        this(distance, null);
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


}
