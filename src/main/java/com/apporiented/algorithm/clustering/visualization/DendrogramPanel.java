package com.apporiented.algorithm.clustering.visualization;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.apporiented.algorithm.clustering.AverageLinkageStrategy;
import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;

public class DendrogramPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final static BasicStroke solidStroke =
	        new BasicStroke(1.0f,
	                        BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_ROUND);
    final static Paint gradPaint = new GradientPaint(0,0,Color.RED,10, 0,Color.WHITE);

	private Cluster model;
	private ClusterComponent component;
	private Color lineColor = Color.BLACK;
	private boolean showDistanceValues= false;
	private int borderTop = 20;
	private int borderLeft = 20;
	private int borderRight = 20;
	private int borderBottom = 20;
	
	private double xModelOrigin = 0.0;
	private double yModelOrigin = 0.0;
	private double wModel = 0.0;
	private double hModel = 0.0;

	
	public boolean isShowDistanceValues() {
		return showDistanceValues;
	}

	public void setShowDistances(boolean showDistanceValues) {
		this.showDistanceValues = showDistanceValues;
	}

	public int getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(int borderTop) {
		this.borderTop = borderTop;
	}

	public int getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(int borderLeft) {
		this.borderLeft = borderLeft;
	}

	public int getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(int borderRight) {
		this.borderRight = borderRight;
	}

	public int getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(int borderBottom) {
		this.borderBottom = borderBottom;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Cluster getModel() {
		return model;
	}

	public void setModel(Cluster model) {
		this.model = model;
		component = createComponent(model);
		updateModelMetrics();
	}

	private void updateModelMetrics() {
	    double minX = component.getRectMinX();
	    double maxX = component.getRectMaxX();
	    double minY = component.getRectMinY();
        double maxY = component.getRectMaxY();

        xModelOrigin = minX;
        yModelOrigin = minY;
        wModel = maxX - minX;
        hModel = maxY - minY;
    }

	
	private ClusterComponent createComponent(Cluster cluster, VCoord initCoord, double clusterHeight) {
	    
	    ClusterComponent comp = null;
	    if (cluster != null) {
	        comp = new ClusterComponent(cluster, cluster.isLeaf(), initCoord);
	        double leafHeight = clusterHeight / cluster.countLeafs();
	        
	        /* Children's y start here */
	        double yChild = initCoord.getY() - (clusterHeight / 2);
            double distance = cluster.getDistance() == null ? 0 : cluster.getDistance();
	        for (Cluster child : cluster.getChildren()) {
                int childLeafCount = child.countLeafs();
                double childHeight = childLeafCount * leafHeight;
                
                double childDistance = child.getDistance() == null ? 0 : child.getDistance();
                VCoord childInitCoord = new VCoord(initCoord.getX() + (distance - childDistance) , yChild + childHeight / 2.0);
                yChild += childHeight;
                
                /* Traverse cluster node tree */
                ClusterComponent childComp = createComponent(child, childInitCoord, childHeight);
                
                // TODO Fix redundancy here!
                childComp.setLinkPoint(initCoord);
                comp.getChildren().add(childComp);
            }
	    }
	    return comp;
	    
	}
	
    private ClusterComponent createComponent(Cluster model) {
		
		double virtualModelHeight = 1; 
		VCoord initCoord = new VCoord(0, virtualModelHeight / 2);
		
		ClusterComponent comp = createComponent(model, initCoord, virtualModelHeight);
		comp.setLinkPoint(initCoord);
		return comp;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(lineColor);
        g2.setStroke(solidStroke);
 
        int wDisplay = getWidth() - borderLeft - borderRight;
        int hDisplay = getHeight() - borderTop - borderBottom;
        int xDisplayOrigin = borderLeft;
        int yDisplayOrigin = borderBottom;

		if (component != null) {
		    
		    int nameGutterWidth = component.getMaxNameWidth(g2, false) + component.getNamePadding();
            wDisplay -= nameGutterWidth;
            
            /* Calculate conversion factor and offset for display */
            double xFactor = wDisplay / wModel;
            double yFactor = hDisplay / hModel;
            int xOffset = (int)(xDisplayOrigin - xModelOrigin * xFactor);
            int yOffset = (int)(yDisplayOrigin - yModelOrigin * yFactor);
	        component.paint(g2, xOffset, yOffset, xFactor, yFactor);
		}
		else {

            /* No data available */
		    String str = "No data";
		    Rectangle2D rect =  g2.getFontMetrics().getStringBounds(str, g2);
		    int xt = (int)(wDisplay / 2.0 - rect.getWidth() / 2.0 );
		    int yt = (int)(hDisplay / 2.0 - rect.getHeight() / 2.0);
		    g2.drawString(str, xt, yt);
		}
	}

    public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 300);
		frame.setLocation(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel content = new JPanel();
		DendrogramPanel dp = new DendrogramPanel();

		frame.setContentPane(content);
		content.setBackground(Color.red);
		content.setLayout(new BorderLayout());
		content.add(dp, BorderLayout.CENTER);
		dp.setBackground(Color.WHITE);
		dp.setLineColor(Color.BLACK);
		
	    Cluster cluster = createSampleCluster();
		dp.setModel(cluster);
		frame.setVisible(true);
	}

    private static Cluster createSampleCluster() {
        double[][] distances = new double[][] { { 0, 1, 9, 7, 11, 14 },
	                { 1, 0, 4, 3, 8, 10 }, { 9, 4, 0, 9, 2, 8 },
	                { 7, 3, 9, 0, 6, 13 }, { 11, 8, 2, 6, 0, 10 },
	                { 14, 10, 8, 13, 10, 0 } };
	    String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
	    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(distances, names,
                new AverageLinkageStrategy());
        cluster.toConsole(0);
        return cluster;
    }

}
