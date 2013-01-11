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
