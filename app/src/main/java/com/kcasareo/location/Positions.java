package com.kcasareo.location;

import java.util.ArrayList;

/**
 * Created by Kevin on 3/09/2017.
 */

public class Positions {
    protected ArrayList<Entry> entries;

    public Positions() {

    }

    public void add(Position node1, Position node2, Position node3) {
        entries.add(new Entry(node1, node2, node3));
    }

    protected class Entry {
        private Nodes nodes;
        public Entry(Position node1, Position node2, Position node3) {
            nodes.add(new Node(node1));
            nodes.add(new Node(node2));
            nodes.add(new Node(node3));

        }
    }
}
