package com.kcasareo.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 23/08/2017.
 * Node positions tracked by the location service.
 */

public class Nodes {
    public HashMap<String, Integer> memo = new HashMap<String, Integer>();
    private ArrayList<Node> nodes;

    public Nodes() {
        nodes = new ArrayList<Node>();
    }

    public Node get(String UUID) {
        int i;
        try {
            // Check if a mapped entry already exists.
            i = memo.get(UUID);
            return nodes.get(i);
        } catch (NullPointerException e) {
            i = 0;
            for (Node node : nodes) {
                i++;
                if (node.matches(UUID)) {
                    memo.put(UUID, i);
                    return node;
                }
                return null;
            }
        }
        return null;
    }

    public void add(Node node) {
        nodes.add(node);
        flush();
    }

    private void flush() {
        memo.clear();
    }

    public void sort() {
        Collections.sort(nodes);
        flush();
    }

    public List<Node> closest() {
        return nodes.subList(0, 2);
    }
}
