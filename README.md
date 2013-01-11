hierarchical-clustering-java
============================

Implementation of an agglomerative hierarchical clustering algorithm in Java. Different linkage approaches are supported.

What you put in
---------------

Pass an distance matrix and a cluster name array to the clustering algorithm:

    String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
    double[][] distances = new double[][] { 
        { 0, 1, 9, 7, 11, 14 },
        { 1, 0, 4, 3, 8, 10 }, 
        { 9, 4, 0, 9, 2, 8 },
        { 7, 3, 9, 0, 6, 13 }, 
        { 11, 8, 2, 6, 0, 10 },
        { 14, 10, 8, 13, 10, 0 }};

    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
    Cluster cluster = alg.performClustering(distances, names,
        new AverageLinkageStrategy());

What you get out
----------------

A java object *Cluster* is returned, that represents an hierachy of clusters based on their distances.
You may want to visualize the result using the Swing component *DendrogramPanel*:

    DendrogramPanel dp = new DendrogramPanel();
    dp.setModel(cluster);

Embedded into a JFrame you should see this:

![Screenshot](https://raw.github.com/lbehnke/hierarchical-clustering-java/master/etc/screenshot1.png "Screenshot of sample clustering result")
