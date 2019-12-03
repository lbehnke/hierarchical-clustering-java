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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class ClusterPair<T> implements Comparable<ClusterPair<T>> {

    static private AtomicLong globalIndex = new AtomicLong(0);

    private Cluster<T> lCluster;
    private Cluster<T> rCluster;
    private Double linkageDistance;

    private Function<T, String> toStringFunction;

    public ClusterPair(){
    }


    public ClusterPair(Cluster<T> left, Cluster<T> right, Double distance, Function<T, String> toStringFunction) {
        lCluster = left;
        rCluster = right;
        linkageDistance = distance;
        this.toStringFunction = toStringFunction;
    }

    public Cluster<T> getOtherCluster(Cluster<T> c) {
        return lCluster == c ? rCluster : lCluster;
    }

    public Cluster<T> getlCluster() {
        return lCluster;
    }

    public void setlCluster(Cluster<T> lCluster) {
        this.lCluster = lCluster;
    }

    public Cluster<T> getrCluster() {
        return rCluster;
    }

    public void setrCluster(Cluster<T> rCluster) {
        this.rCluster = rCluster;
    }

    public Double getLinkageDistance() {
        return linkageDistance;
    }

    public void setLinkageDistance(Double distance) {
        this.linkageDistance = distance;
    }

    /**
     * @return a new ClusterPair with the two left/right inverted
     */
    public ClusterPair<T> reverse() {
        return new ClusterPair<T>(getrCluster(), getlCluster(), getLinkageDistance(),toStringFunction);
    }



    @Override
    public int compareTo(ClusterPair<T> o) {
        int result;
        if (o == null || o.getLinkageDistance() == null) {
            result = -1;
        } else if (getLinkageDistance() == null) {
            result = 1;
        } else {
            result = getLinkageDistance().compareTo(o.getLinkageDistance());
        }

        return result;
    }


    public Cluster<T> agglomerate(T name) {
        NodeCluster<T> cluster = new NodeCluster<>("clstr#" + globalIndex.incrementAndGet());
//            ;
//        if (name == null) {
//            cluster = new NodeCluster("clstr#" + (++globalIndex));
//
//            /*
//            StringBuilder sb = new StringBuilder();
//            if (lCluster != null) {
//                sb.append(lCluster.getName());
//            }
//            if (rCluster != null) {
//                if (sb.length() > 0) {
//                    sb.append("&");
//                }
//                sb.append(rCluster.getName());
//            }
//            name = sb.toString();
//            */
//        } else {
//            cluster = new Cluster<>(name,toStringFunction);
//        }

        cluster.setDistance(new Distance(getLinkageDistance()));
        //New clusters will track their children's leaf names; i.e. each cluster knows what part of the original data it contains
        cluster.appendLeafNames(lCluster.getLeafNames());
        cluster.appendLeafNames(rCluster.getLeafNames());
        cluster.addChild(lCluster);
        cluster.addChild(rCluster);
        lCluster.setParent(cluster);
        rCluster.setParent(cluster);

        Double lWeight = lCluster.getWeightValue();
        Double rWeight = rCluster.getWeightValue();
        double weight = lWeight + rWeight;
        cluster
            .getDistance()
            .setWeight(weight);

        return cluster;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lCluster != null) {
            sb.append(lCluster.getClusterAsString());
        }
        if (rCluster != null) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(rCluster.getClusterAsString());
        }
        sb
            .append(" : ")
            .append(linkageDistance);
        return sb.toString();
    }

}
