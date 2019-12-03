package com.apporiented.algorithm.clustering;

import java.util.*;

/**
 * Container for linkages
 * with the minimal methods needed in the package
 * Created by Alexandre Masselot on 7/18/14.
 */
public class DistanceMap<T> {


    private Map<String, Item> pairHash;
    private TreeSet<Item> data;


    //start here as low as possible
    private static long nextUniqueNum =Long.MIN_VALUE;

    private class Item implements Comparable<Item> {

        //uniquenumber, see in compareTo
        private final long uniqueNum = nextUniqueNum++;

        final ClusterPair<T> pair;
        final String hash;
        boolean removed = false;

        Item(ClusterPair<T> p) {
            pair = p;
            hash = hashCodePair(p);
        }

        @Override
        public int compareTo(Item o) {
            final int compareToPair = pair.compareTo(o.pair);
            if (compareToPair==0){
                //treeset has to have an unique item
                return Long.compare(uniqueNum, o.uniqueNum);
            }
            return compareToPair;
        }

        @Override
        public String toString() {
            return hash;
        }
    }


    public DistanceMap() {
        data = new TreeSet<>();
        pairHash = new HashMap<>();
    }

    public List<ClusterPair<T>> list() {
        List<ClusterPair<T>> l = new ArrayList<ClusterPair<T>>();
        for (Item clusterPair : data) {
            l.add(clusterPair.pair);
        }
        return l;
    }

    public ClusterPair<T> findByCodePair(Cluster<T> c1, Cluster<T> c2) {
        String inCode = hashCodePair(c1, c2);
        try {
            return pairHash.get(inCode).pair;
        } catch (Exception e) {
            throw new RuntimeException("not found hash "+c1.getClusterAsString()+" "+c2.getClusterAsString()+" hash: "+ inCode);
        }
    }

    public ClusterPair<T> removeFirst() {
        Item poll = data.pollFirst();
        while (poll != null && poll.removed) {
            poll = data.pollFirst();
        }
        if (poll == null) {
            return null;
        }
        ClusterPair<T> link = poll.pair;
        pairHash.remove(poll.hash);
        return link;
    }

    public boolean remove(ClusterPair<T> link) {
        final String hashCode = hashCodePair(link);
//        System.err.println("removing hash "+hashCode);
        Item remove = pairHash.remove(hashCode);
        if (remove == null) {
            return false;
        }
        remove.removed = true;
        data.remove(remove);
        return true;
    }


    public boolean add(ClusterPair<T> link) {
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
        Item peek = data.first();
        if(peek!=null)
            return peek.pair.getLinkageDistance();
        else
            return null;
    }

    /**
     * Compute some kind of unique ID for a given cluster pair.
     * @return The ID
     */
    String hashCodePair(ClusterPair<T> link) {
        return hashCodePair(link.getlCluster(), link.getrCluster());
    }



    String hashCodePair(Cluster<T> lCluster, Cluster<T> rCluster) {
        return hashCodePairNames(lCluster.getId(), rCluster.getId());
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
