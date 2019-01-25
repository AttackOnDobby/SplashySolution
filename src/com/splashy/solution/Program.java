package com.splashy.solution;
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

        StringBuffer result = new StringBuffer();
        for (Node node : stack) {
            result.append(node.toString()).append(" -> ");
        }
        result.append(end.toString());

        System.out.println(result);
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
            System.out.println("back");
            if (next == start) {
                System.out.println("from begin");
            }
        } else {
            forward(node, next);
            System.out.println("forward " + next);
        }
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
        int leftIndex = -1;
        int rightIndex = -1;
        int upIndex = -1;
        int downIndex = -1;

        int leftDistance = Integer.MAX_VALUE;
        int rightDistance = Integer.MAX_VALUE;
        int upDistance = Integer.MAX_VALUE;
        int downDistance = Integer.MAX_VALUE;

        for (int i = 0; i < rest.size(); i++) {
            Node item = rest.get(i);

            if (item.getY() == node.getY()) {
                int xDistance = item.getX() - node.getX();
                if (xDistance > 0 && xDistance < rightDistance) {
                    rightIndex = i;
                    rightDistance = xDistance;
                }

                if (xDistance < 0 && -xDistance < leftDistance) {
                    leftIndex = i;
                    leftDistance = -xDistance;
                }
            }

            if (item.getX() == node.getX()) {
                int yDistance = item.getY() - node.getY();
                if (yDistance > 0 && yDistance < upDistance) {
                    upIndex = i;
                    upDistance = yDistance;
                }
                if (yDistance < 0 && -yDistance < downDistance) {
                    downIndex = i;
                    downDistance = -yDistance;
                }
            }
        }

        node.clearAvailable();

        for (int direction = 0; direction < 4; direction++) {
            int index = new int[] {upIndex, downIndex, leftIndex, rightIndex}[direction];

            if (index == -1) {
                continue;
            }
            boolean valid, checkEnd, checkCurrent;
            Node available = rest.get(index);

            checkEnd = checkAvailable(node, available, end, direction);
            checkCurrent = checkAvailable(node, available, stack.lastElement(), direction);

            if (node == start) {
                valid = checkEnd;
            } else {
                valid = checkCurrent && checkEnd;
            }

            if (valid) {
                node.addAvailable(available);
            }
        }
    }

    private boolean checkAvailable(Node node, Node availableNode, Node specialNode, int direction) {

        if (direction == 0) {
            // up
            if (node.getX() == specialNode.getX() && specialNode.getY() > node.getY()) {
                return specialNode.getY() > availableNode.getY();
            } else {
                return true;
            }
        } else if (direction == 1) {
            // down
            if (node.getX() == specialNode.getX() && specialNode.getY() < node.getY()) {
                return specialNode.getY() < availableNode.getY();
            } else {
                return true;
            }
        } else if (direction == 2) {
            // left
            if (node.getY() == specialNode.getY() && specialNode.getX() < node.getX()) {
                return specialNode.getX() < availableNode.getX();
            } else {
                return true;
            }
        } else if (direction == 3) {
            // right
            if (node.getY() == specialNode.getY() && specialNode.getX() > node.getX()) {
                return specialNode.getX() > availableNode.getX();
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
