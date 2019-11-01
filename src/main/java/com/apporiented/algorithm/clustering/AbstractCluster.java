package com.apporiented.algorithm.clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.CONCURRENT;

abstract public class AbstractCluster<T>  implements Cluster<T> {
    private  boolean isNode;
    private NodeCluster parent;
    private List<Cluster<T>> children=new ArrayList<>();
    final private List<T> leafNames=new ArrayList<>();
    private Distance distance = new Distance();

    public AbstractCluster(boolean isNode) {
        this.isNode=isNode;
    }

    //todo: create interface Cluster
    @Override
    public int countLeafs(Cluster<T> node, int count)
    {
        if (node.isLeaf()) count++;
        for (Cluster<T> child : node.getChildren())
        {
            count += child.countLeafs();
        }
        return count;
    }

    @Override
    public void toConsole(int indent)
    {
        for (int i = 0; i < indent; i++)
        {
            System.out.print("  ");

        }
        String name = getClusterAsString() + (isLeaf() ? " (leaf)" : "") + (distance != null ? "  distance: " + distance : "");
        System.out.println(name);
        for (Cluster<T> child : getChildren())
        {
            child.toConsole(indent + 1);
        }
    }

    @Override
    public String toNewickString(int indent)
    {
    	String cdtString = "";
        if(!isLeaf()) cdtString+="(";

    	for (int i = 0; i < indent; i++) cdtString+=" ";


        if(isLeaf()) {
        	cdtString+=getClusterAsString();
        }

        List<Cluster<T>> children = getChildren();

        boolean firstChild = true;
        for (Cluster<T> child : children)
        {
        	cdtString+=child.toNewickString(indent);
        	String distanceString = distance.getDistance().toString().replace(",", ".");
        	String weightString = distance.getWeight().toString().replace(",", ".");
            if(firstChild) cdtString+=":"+distanceString+",";
            else cdtString+=":"+weightString;

            firstChild=false;
        }

        for (int i = 0; i < indent; i++) cdtString+=" ";

        if(!isLeaf()) cdtString+=")";

        return cdtString;
    }

    @Override
    public double getTotalDistance()
    {
        Double dist = getDistance() == null ? 0 : getDistance().getDistance();
        if (getChildren().size() > 0)
        {
            dist += children.get(0).getTotalDistance();
        }
        return dist;

    }

    @Override
    public boolean isNode() {
        return isNode;
    }

    @Override
    public Distance getDistance()
    {
        return distance;
    }

    @Override
    public Double getWeightValue()
    {
        return distance.getWeight();
    }

    @Override
    public Double getDistanceValue()
    {
        return distance.getDistance();
    }

    @Override
    public void setDistance(Distance distance)
    {
        this.distance = distance;
    }

    @Override
    public List<Cluster<T>> getChildren()
    {
        if (children == null)
        {
            children = new ArrayList<Cluster<T>>();
        }

        return children;
    }

    @Override
    public void addLeafName(T lname)
    {
        leafNames.add(lname);
    }

    @Override
    public void appendLeafNames(List<T> lnames)
    {
        leafNames.addAll(lnames);
    }

    @Override
    public List<T> getLeafNames()
    {
        return leafNames;
    }

    @Override
    public void setChildren(List<Cluster<T>> children)
    {
        this.children = children;
    }

    @Override
    public NodeCluster getParent()
    {
        return parent;
    }

    @Override
    public void setParent(NodeCluster parent)
    {
        this.parent = parent;
    }


//    @Override
//    public void setName(T name)
//    {
//        this.name = name;
//    }

    @Override
    public void addChild(Cluster<T> cluster)
    {
        getChildren().add(cluster);

    }

    @Override
    public boolean contains(Cluster<T> cluster)
    {
        return getChildren().contains(cluster);
    }

    @Override
    public boolean isLeaf()
    {
        return !isNode();
    }

    @Override
    public int countLeafs()
    {
        return countLeafs(this, 0);
    }

    @Override
    public Iterator<Cluster<T>> iterator() {
        return getChildren().iterator();
    }

    @Override
    public Spliterator<Cluster<T>> spliterator() {
        return Spliterators.spliterator(new Iterator<Cluster<T>>() {

            LinkedList<Iterator<Cluster<T>>> queue=new LinkedList<>();
            {
                queue.add(getChildren().iterator());
            }

            @Override
            public boolean hasNext() {
                if (!queue.peek().hasNext()){
                    queue.poll();
                }
                return queue.peek()!=null && queue.peek().hasNext();
            }

            @Override
            public Cluster<T> next() {
                if (!queue.peek().hasNext()){
                    queue.poll();
                }
                final Cluster<T> next = queue
                    .peek()
                    .next();
                if (next.isNode()){
                    queue.add(next.getChildren().iterator());
                }
                return next;
            }
        }, 1,CONCURRENT);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @since 1.8
     */
    @Override
    public Stream<Cluster<T>> streamChildren() {
        return StreamSupport.stream(spliterator(), false);
    }


}
