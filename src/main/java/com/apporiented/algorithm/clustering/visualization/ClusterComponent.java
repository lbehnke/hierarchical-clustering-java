package com.apporiented.algorithm.clustering.visualization;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.apporiented.algorithm.clustering.Cluster;


public class ClusterComponent implements Paintable {

	private Cluster cluster;
	private VCoord linkPoint;
	private VCoord initPoint;
    private boolean printName;
    private int dotRadius = 2;
    private int namePadding = 6;

    private List<ClusterComponent> children;

    public List<ClusterComponent> getChildren() {
        if (children == null) {
            children = new ArrayList<ClusterComponent>();
        }
        return children;
    }

    public int getNamePadding() {
        return namePadding;
    }

    public void setNamePadding(int namePadding) {
        this.namePadding = namePadding;
    }
    
    public int getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
    }
    
    public void setChildren(List<ClusterComponent> children) {
        this.children = children;
    }
    
    public VCoord getLinkPoint() {
        return linkPoint;
    }

    public void setLinkPoint(VCoord linkPoint) {
        this.linkPoint = linkPoint;
    }

    public VCoord getInitPoint() {
        return initPoint;
    }

    public void setInitPoint(VCoord initPoint) {
        this.initPoint = initPoint;
    }
    
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public boolean isPrintName() {
		return printName;
	}

	public void setPrintName(boolean printName) {
		this.printName = printName;
	}

	public ClusterComponent (Cluster cluster, boolean printName, VCoord initPoint) {
		this.printName = printName;
		this.cluster = cluster;
		this.initPoint = initPoint;
		this.linkPoint = initPoint;
	}

    @Override
    public void paint(Graphics2D g, int xDisplayOffset, int yDisplayOffset, double xDisplayFactor, double yDisplayFactor) {
        int x1, y1, x2, y2;
        
        x1 = (int)(initPoint.getX() * xDisplayFactor + xDisplayOffset);
        y1 = (int)(initPoint.getY() * yDisplayFactor + yDisplayOffset);
        x2 = (int)(linkPoint.getX() * xDisplayFactor + xDisplayOffset);
        y2 = y1;
        g.fillOval(x1-dotRadius, y1-dotRadius, dotRadius * 2, dotRadius * 2);
        g.drawLine(x1, y1, x2, y2);

        if (cluster.isLeaf()) {
            int hFont =  g.getFontMetrics().getHeight();
            g.drawString(cluster.getName(), x1 + namePadding, y1 + (hFont/2) - 2);
        }
 
        x1 = x2;
        y1 = y2;
        x2 = x1;
        y2 = (int)(linkPoint.getY() * yDisplayFactor + yDisplayOffset);
        g.drawLine(x1, y1, x2, y2);
        
        for (ClusterComponent child : children) {
            child.paint(g, xDisplayOffset, yDisplayOffset, xDisplayFactor, yDisplayFactor);
        }
    }
    
    public double getRectMinX() {
        
        // TODO Better use closure / callback here
        assert initPoint != null && linkPoint != null;
        double val = Math.min(initPoint.getX(), linkPoint.getX());
        for (ClusterComponent child : getChildren()) {
            val = Math.min(val, child.getRectMinX());
        }
        return val;
    }

    public double getRectMinY() {

        // TODO Better use closure here
        assert initPoint != null && linkPoint != null;
        double val = Math.min(initPoint.getY(), linkPoint.getY());
        for (ClusterComponent child : getChildren()) {
            val = Math.min(val, child.getRectMinY());
        }
        return val;
    }
    
    public double getRectMaxX() {

        // TODO Better use closure here
        assert initPoint != null && linkPoint != null;
        double val = Math.max(initPoint.getX(), linkPoint.getX());
        for (ClusterComponent child : getChildren()) {
            val = Math.max(val, child.getRectMaxX());
        }
        return val;
    }

    public double getRectMaxY() {

        // TODO Better use closure here
        assert initPoint != null && linkPoint != null;
        double val = Math.max(initPoint.getY(), linkPoint.getY());
        for (ClusterComponent child : getChildren()) {
            val = Math.max(val, child.getRectMaxY());
        }
        return val;
    }

    public int getNameWidth(Graphics2D g, boolean includeNonLeafs) {
        int width = 0;
        if (includeNonLeafs || cluster.isLeaf()) {
            Rectangle2D rect =  g.getFontMetrics().getStringBounds(cluster.getName(), g);
            width = (int)rect.getWidth();
        }
        return width;
    }
    
    public int getMaxNameWidth(Graphics2D g, boolean includeNonLeafs) {
        int width = getNameWidth(g, includeNonLeafs);
        for (ClusterComponent comp : getChildren()) {
            int childWidth = comp.getMaxNameWidth(g, includeNonLeafs);
            if (childWidth > width) {
                width = childWidth;
            }
        }
        return width;
    }
}
