package com.splashy.solution;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Node> initNodeList() {
        List<Node> nodeList = new ArrayList<>();
        int[][] coors = {
                {0, 1},
                {0, 0},
                {2, 0},
                {4, 0},
                {3, 1},
                {4, 1},
                {0, 2},
                {4, 2},
                {0, 3},
                {0, 4},
                {2, 4},
                {4, 4},
                {0, 5},
                {4, 5},
                {4, 3}
        };

        for (int[] coor : coors) {
            nodeList.add(new Node(coor[0], coor[1]));
        }

        return nodeList;
    }

    public static void main(String[] args) {
        System.out.println("start");

        List<Node> nodeList = initNodeList();
        Node start = nodeList.remove(0);
        Node end = nodeList.remove(nodeList.size() - 1);

        new Program().resolve(nodeList, start, end);
    }
}
