package com.apporiented.algorithm.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Container for linkages (List<ClusterPair>())
 * with the minimal methods needed in the package
 * Created by Alexandre Masselot on 7/18/14.
 */
public class DistanceMap {
    private Map<String, Item> pairHash;
    private PriorityQueue<Item> data;

    private class Item implements Comparable<Item>{
        Item(ClusterPair p){
            pair=p;
            hash = hashCodePair(p);
        }
        final ClusterPair pair;
        final String hash;
        boolean removed=false;
        @Override
        public int compareTo(Item o) {
            return pair.compareTo(o.pair);
        }

    }
    public DistanceMap() {
        data= new PriorityQueue<Item>();
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
        String inCode  = hashCodePair(c1,c2);
        return pairHash.get(inCode).pair;
    }

    public ClusterPair removeFirst() {
        Item poll = data.poll();
        while(poll!=null && poll.removed==true){
            poll=data.poll();
        }
        if(poll==null){
            return null;
        }
        ClusterPair link = poll.pair;
        pairHash.remove(poll.hash);
        return link;
    }

    public boolean remove(ClusterPair link) {
        Item remove = pairHash.remove(hashCodePair(link));
        if(remove==null) {
            return false;
        }
        remove.removed=true;
        return true;
    }

    public boolean add(ClusterPair link) {
        Item e = new Item(link);
        data.add(e);
        Item priorValue = pairHash.put(e.hash, e);
        if (priorValue != null) {
            System.err.println("hashCode = " + priorValue.hash + " adding redundant link:" + link + " (exist:" + priorValue + ")");
        }
        return true;
    }


    /**
     * compute some kind of hashcode, symmetric on the cluster names (used for equality) and regardless of the linkageDistance
     *
     * @return int
     */
    public String hashCodePair(ClusterPair link) {
        return hashCodePair(link.getlCluster(),link.getrCluster());
    }
    public String hashCodePair(Cluster lCluster,Cluster rCluseter) {
        return hashCodePairNames(lCluster.getName(), rCluseter.getName());
    }

    protected String hashCodePairNames(String lName, String rName) {
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
