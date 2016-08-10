package com.apporiented.algorithm.clustering;

import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lars Behnke <lars.behnke@bruker.com>
 */
public class CoordTest {

    private static Cluster importCluster() throws IOException {
        List<Coord> coords = readCoordinates();

        double[][] distances = new double[coords.size()][coords.size()];
        String[] names = new String[coords.size()];
        for (int row = 0; row < coords.size(); row++) {
            Coord coord1 = coords.get(row);
            for (int col = row+1; col < coords.size(); col++) {
                Coord coord2 = coords.get(col);
                double d = Math.sqrt(Math.pow(coord2.getX()-coord1.getX(), 2)+ Math.pow(coord2.getY()-coord1.getY(), 2));
                distances[row][col] = d;
                distances[col][row] = d;
            }
            names[row] = ""+row;
        }
        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(distances, names,
                new AverageLinkageStrategy());
        return cluster;
    }


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setSize(1024, 768);
        frame.setLocation(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        frame.setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.WHITE);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        Cluster cluster = importCluster();
        dp.setModel(cluster);
        frame.setVisible(true);
    }

    private static List<Coord> readCoordinates() throws IOException {
        List<Coord> coordList = new ArrayList<Coord>();
        BufferedReader br = new BufferedReader(new InputStreamReader(CoordTest.class.getResourceAsStream("/testData1.txt")));
        String line;

        while ((line = br.readLine()) != null) {
            String[] elems = line.split(" ");
            if (elems.length != 2) {
                continue;
            }
            int x;
            int y;

            try {
                x = Integer.parseInt(elems[0]);
                y = Integer.parseInt(elems[1]);
            } catch (Exception e) {
                continue;
            }
            coordList.add(new Coord(x, y));
        }
        return coordList;
    }

    public static class Coord {
        private double x;
        private double y;
        public Coord(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
