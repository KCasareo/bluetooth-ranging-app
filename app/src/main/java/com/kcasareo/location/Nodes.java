package com.kcasareo.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kevin on 23/08/2017.
 */

public class Nodes {
    private ArrayList<Node> nodes;

    public Nodes() {
        nodes = new ArrayList<Node>();
    }

    public void add(Node node) {
        nodes.add(node);
    }

    public void sort() {
        Collections.sort(nodes);
    }

    public List<Node> closest() {
        return nodes.subList(0, 2);
    }
}
