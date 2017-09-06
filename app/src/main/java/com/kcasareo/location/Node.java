package com.kcasareo.location;

import android.support.annotation.NonNull;

/**
 * Created by Kevin on 23/08/2017.
 */

public class Node implements Comparable<Node> {
    protected Position position;
    protected String UUID = "";

    public Node(Position position, String UUID) {
        this(position);
        this.UUID = UUID;
    }

    public Node(Position position) {
        this.position = position;
    }

    public Position position() {
        return this.position;
    }

    public void update(double x, double y) {
        position.update(x, y);
    }

    public boolean matches(String UUID) {
        return this.UUID.equals(UUID);
    }

    @Override
    public int compareTo(@NonNull Node other) {
        return this.position.compareTo(other.position());
    }
}
