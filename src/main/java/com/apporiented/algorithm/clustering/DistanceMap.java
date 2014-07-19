package com.apporiented.algorithm.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Container for linkages (List<ClusterPair>())
 * with the minimal methods needed in the package
 * Created by Alexandre Masselot on 7/18/14.
 */
public class DistanceMap {
    private List<ClusterPair> distances;
    private HashMap<String, ClusterPair> pairHash;

    public DistanceMap() {
        distances = new ArrayList<ClusterPair>();
        pairHash = new HashMap<String, ClusterPair>();
    }

    public List<ClusterPair> list() {
        return distances;
    }

    public ClusterPair findByCodePair(Cluster c1, Cluster c2) {
        String inCode  = hashCodePair(new ClusterPair(c1, c2, Double.MAX_VALUE));
        return pairHash.get(inCode);
//        ClusterPair result = null;
//        //System.out.println(">\t" + distances.size() + "\t" + pairHash.size());
//        String inCode = hashCodePair(new ClusterPair(c1, c2, Double.MAX_VALUE));
//        for (ClusterPair link : distances) {
//            if (hashCodePair(link).equals(inCode)) {
//                result = link;
//                break;
//            }
//        }
//        return result;

    }

    public int size() {
        return distances.size();
    }

    public void sort() {
        //System.out.println("sort");
        Collections.sort(distances);
    }

    public ClusterPair remove(int index) {
        ClusterPair link = distances.get(index);
        pairHash.remove(hashCodePair(link));
        return distances.remove(index);
    }

    public boolean remove(ClusterPair link) {
        pairHash.remove(hashCodePair(link));
        return distances.remove(link);
    }

    public boolean add(ClusterPair link) {
        String hash = hashCodePair(link);
        if (pairHash.get(hash) != null) {
            System.err.println("hashCode = " + hash + " adding redundant link:" + link + " (exist:" + pairHash.get(hash) + ")");
        }
        pairHash.put(hashCodePair(link), link);
        return distances.add(link);
    }


    /**
     * compute some kind of hashcode, symmetric on the cluster names (used for equality) and regardless of the linkageDistance
     *
     * @return int
     */
    public String hashCodePair(ClusterPair link) {
        if (link.getlCluster().getName().compareTo(link.getrCluster().getName()) < 0) {
            return link.getlCluster().getName() + "~~~" + link.getrCluster().getName();//getlCluster().hashCode() + 31 * (getrCluster().hashCode());
        } else {
            return link.getrCluster().getName() + "~~~" + link.getlCluster().getName();//return getrCluster().hashCode() + 31 * (getlCluster().hashCode());
        }
    }

    public String toString() {
        return distances.toString();
    }
}
