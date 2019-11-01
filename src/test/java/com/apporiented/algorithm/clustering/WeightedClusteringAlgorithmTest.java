/*******************************************************************************
 * Copyright 2013 Lars Behnke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.apporiented.algorithm.clustering;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class WeightedClusteringAlgorithmTest {
	public static final double[][] DISTANCES = new double[][] { // A--B-C--D
	{ 0, 2, 3, 5 }, { 2, 0, 1, 3 }, { 3, 1, 0, 2 }, { 5, 3, 2, 0 } };
	public static final String[] NAMES = new String[] { "A", "B", "C", "D" };
	public static final double[] WEIGHTSA = new double[] { 1, 100, 1, 1 }; // weight on B, D should be alone
	public static final double[] WEIGHTSD = new double[] { 1, 1, 100, 1 };// weight on C, A should be alone

	@Test
	public void testClusteringAvgLink() {
		ClusteringAlgorithm<String> alg = new DefaultClusteringAlgorithm<String>((Function<String, String>) s -> s);
		Cluster<String> ca = alg.performWeightedClustering(DISTANCES, NAMES, WEIGHTSA, new WeightedLinkageStrategy());
		Assert.assertEquals("D", ca.getChildren().get(0).getClusterAsString());
		Cluster<String> cd = alg.performWeightedClustering(DISTANCES, NAMES, WEIGHTSD, new WeightedLinkageStrategy());
		Assert.assertEquals("A", cd.getChildren().get(0).getClusterAsString());
	}
}
