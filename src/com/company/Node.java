package com.company;

public class Node {
    public Node right;
    public Node left;
    public String info;
    public static int positionInTable = 0;
    public int position;

    public Node(Node right, Node left, String info) {
        this.right = right;
        this.left = left;
        this.info = info;
    }

    public Node(String info){
        this.right = null;
        this.left = null;
        this.info = info;
        this.position = positionInTable;
        positionInTable++;
    }

    public static int getPositionInTable() {
        return positionInTable;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "Node{" +
                "right=" + right +
                ", left=" + left +
                ", info='" + info + '\'' +
                '}';
    }
}
