package com.apporiented.algorithm.clustering;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ClusterPersonTest {

    private static JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    List<Person> personList = Lists.newArrayList(new Person("ANDERS ANDERSSON", Sets.newHashSet("ANDERS@ANDERSSON.SE"), Sets.newHashSet("123456")),
                                                 new Person("ANDER ANDERSSON", Sets.newHashSet("ANDERS@WORK.SE"), Sets.newHashSet("123456")),
                                                 new Person("ANDERS ANDERSON", Sets.newHashSet(), Sets.newHashSet("654321")),
                                                 new Person("BENKE BENKESSON", Sets.newHashSet("BENKE@MAIL.SE"), Sets.newHashSet("478965")));


    @Test
    public void test1() {
        //calculate the distance between them
        double[][] distanceMatrix = new double[personList.size()][personList.size()];
        for (int outer = 0; outer < personList.size(); outer++) {
            for (int inner = outer; inner < personList.size(); inner++) {
                double val = calculateDistanceForPotentialAgents(personList.get(outer), personList.get(inner));
                distanceMatrix[outer][inner] = val;
                distanceMatrix[inner][outer] = val;
            }
        }

        ClusteringAlgorithm<Person> alg = new DefaultClusteringAlgorithm<>(pa -> pa.getName());
        Cluster<Person> cluster = alg.performClustering(distanceMatrix, personList.toArray(new Person[0]), new WeightedLinkageStrategy());



        final List<List<Person>> clusterOfPersons = cluster.streamChildren(30)
                                                           //            .filter(c -> c.getDistanceValue() < 30)
                                                           .map(c -> c
                                                               .stream()
                                                               .filter(cChild -> cChild.isLeaf())
                                                               .map(cChild -> (Person) cChild.getPayload())
                                                               .collect(Collectors.toList()))
                                                           .collect(Collectors.toList());

        List<Person> benke = clusterOfPersons
            .stream()
            .filter(people -> people
                .get(0)
                .getName()
                .equals("BENKE BENKESSON"))
            .findFirst()
            .get();

        assertThat(clusterOfPersons).hasSize(2);
        clusterOfPersons.remove(benke);
        assertThat(benke).containsExactlyInAnyOrder(personList
                                                        .subList(3, 4)
                                                        .toArray(new Person[0]));
        assertThat(benke).containsExactlyInAnyOrder(personList
                                                        .subList(3, 4)
                                                        .toArray(new Person[0]));
        assertThat(clusterOfPersons.get(0)).containsExactlyInAnyOrder(personList
                                                                          .subList(0, 3)
                                                                          .toArray(new Person[0]));

    }

    static private double calculateDistanceForPotentialAgents(Person left, Person right) {

        //if they have the same email address, they are the same
        if (!Sets
            .intersection(left.getEmailAddresses(), right.getEmailAddresses())
            .isEmpty()) {
            return 0d;
        }
        //if they have the same phone numbers, they are the same
        if (!Sets
            .intersection(left.getPhoneNumbers(), right.getPhoneNumbers())
            .isEmpty()) {
            return 0d;
        }


        //calculate difference based on remaining text, i.e. the potential name
        return (1 - jaroWinklerDistance.apply(left.getName(), right.getName())) * 100;
    }

    public static class Person {
        private String name;
        private Set<String> emailAddresses = Sets.newHashSet();
        private Set<String> phoneNumbers = Sets.newHashSet();

        public Person(String name, Set<String> emailAddresses, Set<String> phoneNumbers) {
            this.name = name;
            this.emailAddresses = emailAddresses;
            this.phoneNumbers = phoneNumbers;
        }

        public String getName() {
            return name;
        }

        public Set<String> getEmailAddresses() {
            return emailAddresses;
        }

        public Set<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Person person = (Person) o;
            return Objects.equals(name, person.name) && Objects.equals(emailAddresses, person.emailAddresses) && Objects.equals(phoneNumbers, person.phoneNumbers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, emailAddresses, phoneNumbers);
        }

        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + ", emailAddresses=" + emailAddresses + ", phoneNumbers=" + phoneNumbers + '}';
        }
    }

}
