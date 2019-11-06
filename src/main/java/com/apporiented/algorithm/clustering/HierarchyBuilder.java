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
import java.util.Collection;
import java.util.List;

public class HierarchyBuilder<T> {

    private DistanceMap distances;
    private List<Cluster<T>> clusters;

    public HierarchyBuilder(List<Cluster<T>> clusters, DistanceMap distances) {
        this.clusters = clusters;
        this.distances = distances;
    }

    public DistanceMap getDistances() {
        return distances;
    }

    public List<Cluster<T>> getClusters() {
        return clusters;
    }

    /**
     * Returns Flattened clusters, i.e. clusters that are at least apart by a given threshold
     *
     * @param linkageStrategy
     * @param threshold
     * @return
     */
    public List<Cluster<T>> flatAgg(LinkageStrategy linkageStrategy, Double threshold) {
        while ((!isTreeComplete()) && (distances.minDist() != null) && (distances.minDist() <= threshold)) {
            //System.out.println("Cluster Distances: " + distances.toString());
            //System.out.println("Cluster Size: " + clusters.size());
            agglomerate(linkageStrategy);
        }

        //System.out.println("Final MinDistance: " + distances.minDist());
        //System.out.println("Tree complete: " + isTreeComplete());
        return clusters;
    }

    public void agglomerate(LinkageStrategy linkageStrategy) {
        ClusterPair minDistLink = distances.removeFirst();
        if (minDistLink != null) {
            clusters.remove(minDistLink.getrCluster());
            clusters.remove(minDistLink.getlCluster());

            Cluster<T> oldClusterL = minDistLink.getlCluster();
            Cluster<T> oldClusterR = minDistLink.getrCluster();
            Cluster<T> newCluster = minDistLink.agglomerate(null);

            for (Cluster<T> iClust : clusters) {

                ClusterPair link1 = findByClusters(iClust, oldClusterL);
                ClusterPair link2 = findByClusters(iClust, oldClusterR);
                ClusterPair<T> newLinkage = new ClusterPair<T>();
                newLinkage.setlCluster(iClust);
                newLinkage.setrCluster(newCluster);
                Collection<Distance> distanceValues = new ArrayList<Distance>();

                if (link1 != null) {
                    Double distVal = link1.getLinkageDistance();
                    Double weightVal = link1
                        .getOtherCluster(iClust)
                        .getWeightValue();
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link1);
                }
                if (link2 != null) {
                    Double distVal = link2.getLinkageDistance();
                    Double weightVal = link2
                        .getOtherCluster(iClust)
                        .getWeightValue();
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link2);
                }

                Distance newDistance = linkageStrategy.calculateDistance(distanceValues);

                newLinkage.setLinkageDistance(newDistance.getDistance());
                distances.add(newLinkage);

            }
            clusters.add(newCluster);
        }
    }

    private ClusterPair findByClusters(Cluster<T> c1, Cluster<T> c2) {
        return distances.findByCodePair(c1, c2);
    }

    public boolean isTreeComplete() {
        return clusters.size() == 1;
    }

    public Cluster<T> getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return clusters.get(0);
    }

}
