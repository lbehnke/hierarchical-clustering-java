package com.apporiented.algorithm.clustering.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.apporiented.algorithm.clustering.Cluster;

public class DendrogramPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Cluster model;
	private List<ClusterComponent> components;
	private Color lineColor = Color.BLACK;
	private boolean showDistances = false;
	private int borderTop = 20;
	private int borderLeft = 20;
	private int borderRight = 20;
	private int borderBottom = 20;

	public boolean isShowDistances() {
		return showDistances;
	}

	public void setShowDistances(boolean showDistances) {
		this.showDistances = showDistances;
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
		components = createComponents(model);
	}

	private List<ClusterComponent> createComponents(Cluster model) {
		List<ClusterComponent> comps = new ArrayList<ClusterComponent>();
		
		List<Cluster> leafs = new ArrayList<Cluster>();
		collectLeafs(leafs, model);
		
		int w = getWidth();
		int h = getHeight();
		
		// TODO hier weiter
		
		return comps;
	}
	

	
	private void collectLeafs(List<Cluster> leafs, Cluster cluster) {
		if (cluster.isLeaf()) {
			leafs.add(cluster);
		} 
		else {
			for (Cluster child : cluster.getChildren()) {
				collectLeafs(leafs, child);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(lineColor);

		
		int fh = g2.getFontMetrics().getHeight();
	
		
		
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
		dp.setModel(null);
		frame.setVisible(true);
	}

	
}
