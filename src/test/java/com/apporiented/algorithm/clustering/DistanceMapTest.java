package com.apporiented.algorithm.clustering;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class DistanceMapTest {
    DistanceMap map = new DistanceMap();
	ClusterPair<String> ab = new ClusterPair<String>(new LeafCluster<String>("a", (Function<String, String>) s -> s), new LeafCluster<String>("b", (Function<String, String>) s -> s), 1.0, (Function<String, String>) s -> s);
	ClusterPair<String> bc = new ClusterPair<String>(new LeafCluster<String>("b", (Function<String, String>) s -> s), new LeafCluster<String>("c", (Function<String, String>) s -> s), 2.0, (Function<String, String>) s -> s);
	ClusterPair<String> ca = new ClusterPair<String>(new LeafCluster<String>("c", (Function<String, String>) s -> s), new LeafCluster<String>("a", (Function<String, String>) s -> s), 3.0, (Function<String, String>) s -> s);

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
