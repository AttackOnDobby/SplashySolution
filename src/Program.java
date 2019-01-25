package com.tron.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Program {

    private Stack<Node> stack = new Stack<>();
    private List<Node> rest = new ArrayList<>();

    private Node end;
    private Node start;

    void resolve(List<Node> nodeList, Node start, Node end) {
        stack.push(start);
        rest.clear();
        rest.addAll(nodeList);

        this.end = end;
        this.start = start;

        updateOneNode(start);
        updateRest();

        sniffer(start);
    }

    private boolean sniffer(Node node) {

        if (bingo(node)) {
            System.out.println("bingo");
            return true;
        }

        Node next = node.getAvailable();
        if (next == null) {
            backward();
            next = stack.lastElement();
            if (next == start) {
                System.out.println("from begin");
            }
            System.out.println("back");
        } else {
            forward(node, next);
            System.out.println("forward " + next);
        }
        System.out.println(stack);
        return sniffer(next);
    }

    private void forward(Node from, Node to) {
        stack.push(to);
        rest.remove(to);

        from.removeAvailable(to);
        updateRest();
    }

    private void backward() {
        Node node = stack.pop();
        rest.add(node);
        updateRest();
    }

    private void updateRest() {
        for (Node node : rest) {
            updateOneNode(node);
        }
    }

    private void updateOneNode(Node node) {
        int left = -1;
        int right = -1;
        int up = -1;
        int down = -1;

        int leftDistance = Integer.MAX_VALUE;
        int rightDistance = Integer.MAX_VALUE;
        int upDistance = Integer.MAX_VALUE;
        int downDistance = Integer.MAX_VALUE;

        for (int i = 0; i < rest.size(); i++) {
            Node item = rest.get(i);

            if (item.getY() == node.getY()) {
                int xDistance = item.getX() - node.getX();
                if (xDistance > 0 && xDistance < rightDistance) {
                    right = i;
                    rightDistance = xDistance;
                }

                if (xDistance < 0 && -xDistance < leftDistance) {
                    left = i;
                    leftDistance = -xDistance;
                }
            }

            if (item.getX() == node.getX()) {
                int yDistance = item.getY() - node.getY();
                if (yDistance > 0 && yDistance < upDistance) {
                    up = i;
                    upDistance = yDistance;
                }
                if (yDistance < 0 && -yDistance < downDistance) {
                    down = i;
                    downDistance = -yDistance;
                }
            }
        }

        node.clearAvailable();
        for (int i : new int[] {up, down, left, right}) {
            if (i > 0) {
                if (checkAvailable(node, rest.get(i), i)) {
                    node.addAvailable(rest.get(i));
                }
            }
        }
    }

    private boolean checkAvailable(Node node, Node availableNode, int direction) {
        Node currentNode = stack.lastElement();
        if (currentNode == start) {
            return true;
        }
        if (direction == 0) {
            // up
            if (node.getX() == currentNode.getX()) {
                return currentNode.getY() > availableNode.getY();
            } else {
                return true;
            }
        } else if (direction == 1) {
            // down
            if (node.getX() == currentNode.getX()) {
                return currentNode.getY() < availableNode.getY();
            } else {
                return true;
            }
        } else if (direction == 2) {
            // left
            if (node.getY() == currentNode.getY()) {
                return currentNode.getX() < availableNode.getX();
            } else {
                return true;
            }
        } else if (direction == 3) {
            // right
            if (node.getY() == currentNode.getY()) {
                return currentNode.getX() > availableNode.getX();
            } else {
                return true;
            }
        }

        return false;
    }

    private boolean bingo(Node node) {
        return (rest.size() == 0 && (node.getX() == end.getX() || node.getY() == end.getY()));
    }
}
