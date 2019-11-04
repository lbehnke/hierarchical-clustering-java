package com.apporiented.algorithm.clustering;

import java.util.List;
import java.util.stream.Stream;

public interface Cluster<T> extends Iterable<Cluster<T>>{
    //todo: create interface Cluster
    int countLeafs(Cluster<T> node, int count);

    void toConsole(int indent);

    String getClusterAsString();

    String toNewickString(int indent);

    double getTotalDistance();

    boolean isNode();

    Distance getDistance();

    Double getWeightValue();

    Double getDistanceValue();

    void setDistance(Distance distance);

    List<Cluster<T>> getChildren();

    void addLeafName(T lname);

    void appendLeafNames(List<T> lnames);

    List<T> getLeafNames();

    void setChildren(List<Cluster<T>> children);

    NodeCluster getParent();

    void setParent(NodeCluster parent);

//    T getName();
//
//    void setName(T name);

    void addChild(Cluster<T> cluster);

    boolean contains(Cluster<T> cluster);

    boolean isLeaf();

    int countLeafs();

    Object getPayload();

    Stream<Cluster<T>> stream();

    Stream<Cluster<T>> stream(double untilNodeDistanceLessThan);

    Stream<Cluster<T>> streamChildren();

    Stream<Cluster<T>> streamChildren(double untilNodeDistanceLessThan);
}
