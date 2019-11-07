package com.apporiented.algorithm.clustering;

import java.util.Objects;

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

    @Override
    public String getId() {
        return String.valueOf(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeCluster<?> that = (NodeCluster<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
