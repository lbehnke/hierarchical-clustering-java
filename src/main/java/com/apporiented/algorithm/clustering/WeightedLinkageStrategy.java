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

import java.util.Collection;

public class WeightedLinkageStrategy implements LinkageStrategy {

    @Override
    public Distance calculateDistance(Collection<Distance> distances) {
        double sum = 0;
        double weightTotal = 0;
        for (Distance distance : distances) {
            weightTotal += distance.getWeight();
            sum += distance.getDistance() * distance.getWeight();
        }

		return new Distance(sum / weightTotal, weightTotal);
    }
}
