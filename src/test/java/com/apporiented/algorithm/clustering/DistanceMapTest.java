package com.apporiented.algorithm.clustering;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceMapTest {
    DistanceMap map = new DistanceMap();
    ClusterPair ab = new ClusterPair(new Cluster("a"), new Cluster("b"), 1.0);
    ClusterPair bc = new ClusterPair(new Cluster("b"), new Cluster("c"), 2.0);
    ClusterPair ca = new ClusterPair(new Cluster("c"), new Cluster("a"), 3.0);

    @Test
    public void testMapWorksWithSameDistance() throws Exception {
        this.map.add(ab);
        this.map.add(ab);  //add the same link twice. This seems to be an error case
        assertEquals(1,this.map.list().size());
        ClusterPair remove = this.map.removeFirst();
        assertNotNull(remove);
        assertEquals(0,this.map.list().size());  //still exists in the map(even though removeFirst will return null now)
        ClusterPair remove2 = this.map.removeFirst();
        assertNull(remove2);
    }
    @Test
    public void testMapRemovalFront() throws Exception {
        this.map.add(ca);
        this.map.add(bc);
        this.map.add(ab);

        ClusterPair removeFirst = this.map.removeFirst();
        assertEquals(ab, removeFirst);
    }
    @Test
    public void testMapRemovalByObjectPollLoop() throws Exception {
        this.map.add(ca);
        this.map.add(bc);
        this.map.add(ab);

        assertTrue(this.map.remove(ab)); //Doesn't actually remove from prioQueue
        ClusterPair removeFirst = this.map.removeFirst();
        assertEquals(bc, removeFirst);  //removeFirst should now skip the ab
    }
    @Test
    public void testMapRemovalByObjectPollLoopHandlesAllEmpty() throws Exception {
        this.map.add(ca);
        this.map.add(bc);
        this.map.add(ab);

        assertTrue(this.map.remove(ab)); //Doesn't actually remove from prioQueue
        assertTrue(this.map.remove(bc)); //Doesn't actually remove from prioQueue
        assertTrue(this.map.remove(ca)); //Doesn't actually remove from prioQueue
        assertFalse(this.map.remove(ab)); //Doesn't actually remove from prioQueue
        ClusterPair removeFirst = this.map.removeFirst();
        assertNull(removeFirst);  //removeFirst should now skip the ab
    }
}
