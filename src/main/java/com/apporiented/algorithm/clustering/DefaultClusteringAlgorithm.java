/*******************************************************************************
 * Copyright 2013 Lars Behnke
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultClusteringAlgorithm<T> implements ClusteringAlgorithm<T>
{

    private final Function<T, String> toIdFunction;
    private Function<T, String> toStringFunction;

    public DefaultClusteringAlgorithm(Function<T, String> toStringFunction) {
        this(toStringFunction,null);
    }

    public DefaultClusteringAlgorithm(Function<T, String> toStringFunction, /*@Nullable*/ Function<T, String> toIdFunction) {
        this.toIdFunction = toIdFunction ==null? t -> String.valueOf(t.hashCode()) : toIdFunction;
        this.toStringFunction = toStringFunction;
    }

    @Override
    public Function<T, String> getToStringFunction() {
        return toStringFunction;
    }

    @Override
    public Function<T, String> getToIdFunction() {
        return toIdFunction;
    }

    @Override
    public Cluster<T> performClustering(double[][] distances,
                                        T[] clusterNames, LinkageStrategy linkageStrategy)
    {

        checkArguments(distances, clusterNames, linkageStrategy);
        // create all leaves
        List<Cluster<T>> clusters = createClusters(clusterNames);
        //create link between all clusters
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

    @Override
    public List<LeafCluster<T>> performFlatClustering(double[][] distances,
                                                      T[] clusterNames, LinkageStrategy linkageStrategy, Double threshold)
    {

        checkArguments(distances, clusterNames, linkageStrategy);
    /* Setup model */
        List<Cluster<T>> clusters = createClusters(clusterNames);
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        return builder.flatAgg(linkageStrategy, threshold);
    }

    private void checkArguments(double[][] distances, T[] clusterNames,
                                LinkageStrategy linkageStrategy)
    {
        if (distances == null || distances.length == 0
                || distances[0].length != distances.length)
        {
            throw new IllegalArgumentException("Invalid distance matrix");
        }
        if (distances.length != clusterNames.length)
        {
            throw new IllegalArgumentException("Invalid cluster name array");
        }
        if (linkageStrategy == null)
        {
            throw new IllegalArgumentException("Undefined linkage strategy");
        }
        int uniqueCount = Stream.of(clusterNames).map(c->toStringFunction.apply(c)).collect(Collectors.toSet()).size();
        if (uniqueCount != clusterNames.length)
        {
            throw new IllegalArgumentException("Duplicate names");
        }
    }

    @Override
    public Cluster<T> performWeightedClustering(double[][] distances, T[] clusterNames,
                                                double[] weights, LinkageStrategy linkageStrategy)
    {

        checkArguments(distances, clusterNames, linkageStrategy);

        if (weights.length != clusterNames.length)
        {
            throw new IllegalArgumentException("Invalid weights array");
        }

    /* Setup model */
        List<Cluster<T>> clusters = createClusters(clusterNames, weights);
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

    private DistanceMap createLinkages(double[][] distances,
                                       List<Cluster<T>> clusters)
    {
        DistanceMap linkages = new DistanceMap();
        for (int col = 0; col < clusters.size(); col++)
        {
            for (int row = col + 1; row < clusters.size(); row++)
            {
                ClusterPair<T> link = new ClusterPair();
                Cluster<T> lCluster = clusters.get(col);
                Cluster<T> rCluster = clusters.get(row);
                link.setLinkageDistance(distances[col][row]);
                link.setlCluster(lCluster);
                link.setrCluster(rCluster);
                linkages.add(link);
            }
        }
        return linkages;
    }

    private List<Cluster<T>> createClusters(T[] clusterNames)
    {
        List<Cluster<T>> clusters = new ArrayList<>();
        for (T clusterName : clusterNames)
        {
            LeafCluster<T> cluster = new LeafCluster<T>(clusterName, toStringFunction,getToIdFunction());
            clusters.add(cluster);
        }
        return clusters;
    }

    private List<Cluster<T>> createClusters(T[] clusterNames, double[] weights)
    {
        List<Cluster<T>> clusters = new ArrayList<>();
        for (int i = 0; i < weights.length; i++)
        {
            LeafCluster<T> cluster = new LeafCluster<T>(clusterNames[i], toStringFunction,getToIdFunction());
            cluster.setDistance(new Distance(0.0, weights[i]));
            clusters.add(cluster);
        }
        return clusters;
    }

}
