package com.apporiented.algorithm.clustering.visualization;

import java.awt.Graphics2D;

/** 
 * Implemented by visual components of the dendrogram.
 * @author lars
 *
 */
public interface Paintable {

    void paint(Graphics2D g, int xDisplayOffset, int yDisplayOffset, double xDisplayFactor, double yDisplayFactor, boolean decorated);
    
}
