package com.apporiented.algorithm.clustering;

import java.util.Collection;

public class SingleLinkageStrategy implements LinkageStrategy {

	@Override
	public Double calculateDistance(Collection<Double> distances) {
		double min = Double.NaN;

		for (Double dist : distances) {
		    if (Double.isNaN(min) || dist < min)
		        min = dist;
		}
		return min;
	}
}
