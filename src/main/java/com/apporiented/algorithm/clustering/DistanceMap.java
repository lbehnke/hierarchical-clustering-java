package com.apporiented.algorithm.clustering;

import java.util.*;

/**
 * Container for linkages
 * with the minimal methods needed in the package
 * Created by Alexandre Masselot on 7/18/14.
 */
public class DistanceMap {

    private Map<String, Item> pairHash;
    private PriorityQueue<Item> data;

    private class Item implements Comparable<Item> {
        final ClusterPair pair;
        final String hash;
        boolean removed = false;

        Item(ClusterPair p) {
            pair = p;
            hash = hashCodePair(p);
        }

        @Override
        public int compareTo(Item o) {
            return pair.compareTo(o.pair);
        }

        @Override
        public String toString() {
            return hash;
        }
    }

    public DistanceMap() {
        data = new PriorityQueue<Item>();
        pairHash = new HashMap<String, Item>();
    }

    public List<ClusterPair> list() {
        List<ClusterPair> l = new ArrayList<ClusterPair>();
        for (Item clusterPair : data) {
            l.add(clusterPair.pair);
        }
        return l;
    }

    public ClusterPair findByCodePair(Cluster c1, Cluster c2) {
        String inCode = hashCodePair(c1, c2);
        return pairHash.get(inCode).pair;
    }

    public ClusterPair removeFirst() {
        Item poll = data.poll();
        while (poll != null && poll.removed) {
            poll = data.poll();
        }
        if (poll == null) {
            return null;
        }
        ClusterPair link = poll.pair;
        pairHash.remove(poll.hash);
        return link;
    }

    public boolean remove(ClusterPair link) {
        Item remove = pairHash.remove(hashCodePair(link));
        if (remove == null) {
            return false;
        }
        remove.removed = true;
        data.remove(remove);
        return true;
    }


    public boolean add(ClusterPair link) {
        Item e = new Item(link);
        Item existingItem = pairHash.get(e.hash);
        if (existingItem != null) {
            System.err.println("hashCode = " + existingItem.hash + " adding redundant link:" + link + " (exist:" + existingItem + ")");
            return false;
        } else {
            pairHash.put(e.hash, e);
            data.add(e);
            return true;
        }
    }

    /**
     * Peak into the minimum distance
     * @return
     */
    public Double minDist()
    {
        Item peek = data.peek();
        if(peek!=null)
            return peek.pair.getLinkageDistance();
        else
            return null;
    }

    /**
     * Compute some kind of unique ID for a given cluster pair.
     * @return The ID
     */
    String hashCodePair(ClusterPair link) {
        return hashCodePair(link.getlCluster(), link.getrCluster());
    }

    String hashCodePair(Cluster lCluster, Cluster rCluster) {
        return hashCodePairNames(lCluster.getName(), rCluster.getName());
    }

    String hashCodePairNames(String lName, String rName) {
        if (lName.compareTo(rName) < 0) {
            return lName + "~~~" + rName;//getlCluster().hashCode() + 31 * (getrCluster().hashCode());
        } else {
            return rName + "~~~" + lName;//return getrCluster().hashCode() + 31 * (getlCluster().hashCode());
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
