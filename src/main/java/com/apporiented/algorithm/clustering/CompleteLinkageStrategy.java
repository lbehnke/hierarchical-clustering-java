package com.apporiented.algorithm.clustering;

import java.util.Collection;

public class CompleteLinkageStrategy implements LinkageStrategy {

	@Override
	public Double calculateDistance(Collection<Double> distances) {
		double max = Double.NaN;

		for (Double dist : distances) {
		    if (Double.isNaN(max) || dist > max)
		        max = dist;
		}
		return max;
	}
}
