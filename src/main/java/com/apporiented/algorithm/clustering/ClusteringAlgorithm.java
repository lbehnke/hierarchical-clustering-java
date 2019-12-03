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

import java.util.List;
import java.util.function.Function;

public interface ClusteringAlgorithm<T>
{


    Function<T, String> getToStringFunction();

    default Function<T, String> getToIdFunction(){
        return t -> String.valueOf(t.hashCode());
    }

    public Cluster<T> performClustering(double[][] distances, T[] clusterNames,
                                        LinkageStrategy linkageStrategy);

    public Cluster<T> performWeightedClustering(double[][] distances, T[] clusterNames,
                                                double[] weights, LinkageStrategy linkageStrategy);

    public List<LeafCluster<T>> performFlatClustering(double[][] distances,
                                                      T[] clusterNames, LinkageStrategy linkageStrategy, Double threshold);
}
