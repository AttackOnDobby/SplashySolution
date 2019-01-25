package com.splashy.soution;

import java.util.ArrayList;
import java.util.List;


public class Node {

    private int x;
    private int y;

    private List<Node> availableList = new ArrayList<>();

    private boolean inQueue;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void addAvailable(Node node) {
        availableList.add(node);
    }

    void removeAvailable(Node node) {
        availableList.remove(node);
    }

    void clearAvailable() {
        this.availableList.clear();
    }

    Node getAvailable() {
        if (availableList.size() == 0) {
            return null;
        }

        return availableList.get(0);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }
}
