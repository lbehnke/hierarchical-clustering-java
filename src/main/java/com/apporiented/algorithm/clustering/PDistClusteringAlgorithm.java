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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PDistClusteringAlgorithm<T> implements ClusteringAlgorithm<T> {

    private Function<T, String> toStringFunction;

    public PDistClusteringAlgorithm(Function<T, String> toStringFunction) {

        this.toStringFunction = toStringFunction;
    }

    @Override
    public Function<T, String> getToStringFunction() {
        return toStringFunction;
    }

    @Override
    public Cluster<T> performClustering(double[][] distances,
                                        T[] clusterNames, LinkageStrategy linkageStrategy) {

		/* Argument checks */
        if (distances == null || distances.length == 0) {
            throw new IllegalArgumentException("Invalid distance matrix");
        }
        if (distances[0].length != clusterNames.length
                * (clusterNames.length - 1) / 2) {
            throw new IllegalArgumentException("Invalid cluster name array");
        }
        if (linkageStrategy == null) {
            throw new IllegalArgumentException("Undefined linkage strategy");
        }

		/* Setup model */
        List<LeafCluster<T>> clusters = createClusters(clusterNames);
        DistanceMap linkages = createLinkages(distances, clusters);

		/* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete()) {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

    @Override
    public List<LeafCluster<T>> performFlatClustering(double[][] distances,
                                                      T[] clusterNames, LinkageStrategy linkageStrategy, Double threshold) {

		/* Argument checks */
        if (distances == null || distances.length == 0) {
            throw new IllegalArgumentException("Invalid distance matrix");
        }
        if (distances[0].length != clusterNames.length
                * (clusterNames.length - 1) / 2) {
            throw new IllegalArgumentException("Invalid cluster name array");
        }
        if (linkageStrategy == null) {
            throw new IllegalArgumentException("Undefined linkage strategy");
        }

		/* Setup model */
        List<LeafCluster<T>> clusters = createClusters(clusterNames);
        DistanceMap linkages = createLinkages(distances, clusters);

		/* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        return builder.flatAgg(linkageStrategy, threshold);
    }

    @Override
    public Cluster<T> performWeightedClustering(double[][] distances, T[] clusterNames,
                                                double[] weights, LinkageStrategy linkageStrategy) {
        return performClustering(distances, clusterNames, linkageStrategy);
    }

    private DistanceMap createLinkages(double[][] distances,
                                       List<LeafCluster<T>> clusters) {
        DistanceMap linkages = new DistanceMap();
        for (int col = 0; col < clusters.size(); col++) {
            LeafCluster cluster_col = clusters.get(col);
            for (int row = col + 1; row < clusters.size(); row++) {
                ClusterPair<T> link = new ClusterPair<T>();
                Double d = distances[0][accessFunction(row, col,
                        clusters.size())];
				link.setLinkageDistance(d);
                link.setlCluster(cluster_col);
                link.setrCluster(clusters.get(row));
                linkages.add(link);
            }
        }
        return linkages;
    }

    private List<LeafCluster<T>> createClusters(T[] clusterNames) {
        List<LeafCluster<T>> clusters = new ArrayList<LeafCluster<T>>();
        for (T clusterName : clusterNames) {
            LeafCluster<T> cluster = new LeafCluster<T>(clusterName, getToStringFunction(),getToIdFunction());
            cluster.addLeafName(clusterName);
            clusters.add(cluster);
        }
        return clusters;
    }

    // Credit to this function goes to
    // http://stackoverflow.com/questions/13079563/how-does-condensed-distance-matrix-work-pdist
    private static int accessFunction(int i, int j, int n) {
        return n * j - j * (j + 1) / 2 + i - 1 - j;
    }

}
