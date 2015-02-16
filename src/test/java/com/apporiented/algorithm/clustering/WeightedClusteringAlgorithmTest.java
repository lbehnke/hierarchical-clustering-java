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

import static com.apporiented.algorithm.clustering.SampleClusterData.*;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Test;

import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;

public class WeightedClusteringAlgorithmTest {

  @Test
  public void testClusteringAvgLink() {
    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
    Cluster c = alg.performWeightedClustering(DISTANCES, NAMES, WEIGHTS,
        new WeightedLinkageStrategy());
    DendrogramPanel dp = new DendrogramPanel();
    dp.setModel(c);
    JFrame f = new JFrame();
    f.setContentPane(dp);
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Assert.assertNotNull(c);
  }
}
