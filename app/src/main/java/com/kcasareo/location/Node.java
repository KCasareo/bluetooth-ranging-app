package com.kcasareo.location;

import android.support.annotation.NonNull;

/**
 * Created by Kevin on 23/08/2017.
 */

public class Node implements Comparable<Node> {
    protected Position position;
    String UUID;

    public Node(Position position) {
        this.position = position;
    }

    public Position position() {
        return this.position;
    }

    @Override
    public int compareTo(@NonNull Node other) {
        return this.position.compareTo(other.position());
    }
}
