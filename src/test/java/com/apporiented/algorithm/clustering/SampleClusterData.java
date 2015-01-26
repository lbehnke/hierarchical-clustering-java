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

public final class SampleClusterData {

    public static final double[][] DISTANCES = new double[][] { { 0, 1, 9, 7, 11, 14 },
            { 1, 0, 4, 3, 8, 10 }, { 9, 4, 0, 9, 2, 8 },
            { 7, 3, 9, 0, 6, 13 }, { 11, 8, 2, 6, 0, 10 },
            { 14, 10, 8, 13, 10, 0 } };
    public static final String[] NAMES = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
	public static final String[] NAMES_WITH_DUPLICATE = new String[] { "O2", "O2", "O3", "O4", "O5", "O6" };

}
