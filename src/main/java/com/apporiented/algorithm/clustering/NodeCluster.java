package com.apporiented.algorithm.clustering;

public class NodeCluster<T> extends AbstractCluster<T>  implements Cluster<T> {
    private final String name;

    public NodeCluster(java.lang.String name) {
        super(true);
        this.name=name;

    }

    @Override
    public String getClusterAsString() {
        return name;
    }

    @Override
    public Object getPayload() {
        return name;
    }
}
