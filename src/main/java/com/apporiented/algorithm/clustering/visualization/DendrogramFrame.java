package com.apporiented.algorithm.clustering.visualization;

import com.apporiented.algorithm.clustering.*;

import javax.swing.*;
import java.awt.*;


public class DendrogramFrame extends JFrame {

    public DendrogramFrame(Cluster cluster) {
        setSize(500, 400);
        setLocation(100, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.WHITE);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        dp.setModel(cluster);
        setVisible(true);
    }

    public static void main(String[] args) {
        LinkageStrategy strategy = new CompleteLinkageStrategy();
        Frame f1 = new DendrogramFrame(createSampleCluster(strategy));

        Frame f2 = new DendrogramFrame(createSampleCluster2(strategy));
        f2.setLocation(600, 200);
        f2.setSize(700, 800);
    }

    private static Cluster createSampleCluster(LinkageStrategy strategy) {
        double[][] distances = new double[][] {
                { 1, 9, 7, 11, 14, 4, 3, 8, 10, 9, 2, 8, 6, 13, 10 }
        };
        String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
        ClusteringAlgorithm alg = new PDistClusteringAlgorithm();
        Cluster cluster = alg.performClustering(distances, names, strategy);
        cluster.toConsole(0);
        return cluster;
    }

    private static Cluster createSampleCluster2(LinkageStrategy strategy) {
        double[][] distances = new double[][] {
                { 1, 9, 7, 11, 14, 12,   4, 3, 8, 10, 12,   9, 2, 8, 9,   6, 13, 11,   10, 7,   2}
        };
        String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6", "07" };
        ClusteringAlgorithm alg = new PDistClusteringAlgorithm();
        Cluster cluster = alg.performClustering(distances, names, strategy);
        cluster.toConsole(0);
        return cluster;
    }
}
