package com.apporiented.algorithm.clustering;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ListTest {

    double[][] distance = {{0.0,
                            0.0,
                            52.59259259259259,
                            52.59259259259259,
                            51.66666666666666,
                            2.0000000000000018,
                            0.0},
                           {0.0,
                            0.0,
                            52.59259259259259,
                            52.59259259259259,
                            51.66666666666666,
                            2.0000000000000018,
                            0.0},
                           {52.59259259259259,
                            52.59259259259259,
                            0.0,
                            0.0,
                            67.5925925925926,
                            51.85185185185186,
                            52.59259259259259},
                           {52.59259259259259,
                            52.59259259259259,
                            0.0,
                            0.0,
                            67.5925925925926,
                            51.85185185185186,
                            52.59259259259259},
                           {51.66666666666666,
                            51.66666666666666,
                            67.5925925925926,
                            67.5925925925926,
                            0.0,
                            58.796296296296305,
                            51.66666666666666

                           },
                           {2.0000000000000018,
                            2.0000000000000018,
                            51.85185185185186,
                            51.85185185185186,
                            58.796296296296305,
                            0.0,
                            2.0000000000000018},
                           {0.0,
                            0.0,
                            52.59259259259259,
                            52.59259259259259,
                            51.66666666666666,
                            2.0000000000000018,
                            0.0}};
    String[] names = new String[] {"WILHELMSEN FUJAIRAH",
                                   "WILHELMSEN",
                                   "SREEKUMAR VARIER",
                                   "SREEKUMAR VARIER",
                                   "FUJAIRAH",
                                   "WILHELMSE",
                                   "WILHELMSEN FUJAIRAH"};


    @Test
    public void test1() {

        String[][] arr = Stream
            .of(names)
            .map(val -> val.split(" "))
            .collect(Collectors.toList())
            .toArray(new String[0][0]);

        ClusteringAlgorithm<String[]> alg = new DefaultClusteringAlgorithm<>((Function<String[], String>) strings -> Stream
            .of(strings)
            .collect(Collectors.joining(" ")) + System.identityHashCode(strings) + Math.random());
        @SuppressWarnings("unchecked") Cluster<String[]> cluster = alg.performClustering(distance, arr, new WeightedLinkageStrategy());

        List<String[]> collect = cluster
            .streamChildren()
            .filter(c -> c.isLeaf())
            .map(c -> (String[]) c.getPayload())
            .collect(Collectors.toList());

        assertArrayEquals(collect.get(0), new String[] {"FUJAIRAH"});
        assertArrayEquals(collect.get(1),
                          new String[] {"SREEKUMAR",
                                        "VARIER"});
        assertArrayEquals(collect.get(2),
                          new String[] {"SREEKUMAR",
                                        "VARIER"});
        assertArrayEquals(collect.get(3), new String[] {"WILHELMSE"});
        assertArrayEquals(collect.get(4),
                          new String[] {"WILHELMSEN",
                                        "FUJAIRAH"});
        assertArrayEquals(collect.get(5),
                          new String[] {"WILHELMSEN",
                                        "FUJAIRAH"});
        assertArrayEquals(collect.get(6), new String[] {"WILHELMSEN"});
    }

    @Test
    public void test() {
        List<List<String>> namesAsTokens = Stream
            .of(names)
            .map(name -> Lists.newArrayList(name.split(" ")))
            .map(list -> new ArrayList<String>(list) {
                @SuppressWarnings("unchecked")
                @Override
                public int hashCode() {
                    return System.identityHashCode(this);
                }
            })
            .collect(Collectors.toList());

        ClusteringAlgorithm<List<String>> alg = new DefaultClusteringAlgorithm<>(strings -> strings
            .stream()
            .collect(Collectors.joining(" ")) + System.identityHashCode(strings));
        @SuppressWarnings("unchecked") Cluster<List<String>> cluster = alg.performClustering(distance, namesAsTokens.toArray(new List[0]), new WeightedLinkageStrategy());

        List<List<String>> collect = cluster
            .streamChildren()
            .filter(c -> c.isLeaf())
            .map(c -> (List<String>) c.getPayload())
            .collect(Collectors.toList());


        assertEquals(collect.get(0), Lists.newArrayList("FUJAIRAH"));
        assertEquals(collect.get(1), Lists.newArrayList("SREEKUMAR", "VARIER"));
        assertEquals(collect.get(2), Lists.newArrayList("SREEKUMAR", "VARIER"));
        assertEquals(collect.get(3), Lists.newArrayList("WILHELMSE"));
        assertEquals(collect.get(4), Lists.newArrayList("WILHELMSEN", "FUJAIRAH"));
        assertEquals(collect.get(5), Lists.newArrayList("WILHELMSEN", "FUJAIRAH"));
        assertEquals(collect.get(6), Lists.newArrayList("WILHELMSEN"));


    }



}
