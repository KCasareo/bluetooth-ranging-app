package com.kcasareo.location;

import android.support.annotation.NonNull;

/**
 * Created by Kevin on 23/08/2017.
 */

public class Node implements Comparable<Node> {
    protected Position position;
    protected String UUID;

    public Node(Position position, String UUID) {
        this.position = position;
        this.UUID = UUID;
    }

    public Position position() {
        return this.position;
    }

    public boolean matches(String UUID) {
        return this.UUID.equals(UUID);
    }

    @Override
    public int compareTo(@NonNull Node other) {
        return this.position.compareTo(other.position());
    }
}
