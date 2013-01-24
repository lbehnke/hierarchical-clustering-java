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

package com.apporiented.algorithm.clustering.visualization;

/** 
 * Virtual coordinates.
 */
public class VCoord {

    private double x = Double.NaN;
    private double y = Double.NaN;
    
    public VCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VCoord) {
            VCoord other = (VCoord)obj;
            return x == other.getX() && y == other.getY();
        } 
        else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return  String.format("Coord(%.3f,%.3f)", x, y);
    }

}
