package com.apporiented.algorithm.custering;

import java.util.Collection;

public class AverageLinkageStrategy implements LinkageStrategy {

	@Override
	public Double calculateDistance(Collection<Double> distances) {
		double sum = 0;
		double result;

		for (Double dist : distances) {
			sum += dist;
		}
		if (distances.size() > 0) {
			result = sum / distances.size();
		} else {
			result = 0.0;
		}
		return result;
	}
}
