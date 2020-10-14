package com.company;

public class SymbolTable {
    private Node root;
    public SymbolTable(){
        root = null;
    }

    public SymbolTable(Node root) {
        this.root = root;
    }

    public Boolean findNode(String info){
        //tree is empty or we have the info at the root
        if(root == null || root.info.compareTo(info) == 0)
            return true;
        Node currentNode = this.root;
        Boolean found = false;
        //if the tree is not empty, we compare the new info to see in which direction we need to go down the tree
        while(currentNode != null && !found){
            if(currentNode.info.compareTo(info) == 0)
                found = true;
            else if(root.getInfo().compareTo(info) > 0)
                currentNode = root.getLeft();
            else
                currentNode = root.getRight();
        }
        return found;
    }

    public void insertNode(String info){
        this.root = this.insertRec(root, info);
    }

    public Node insertRec(Node root, String info){
        if (root == null){
            this.root = new Node(info);
            return root;
        }
        if(info.compareTo(root.info) > 0)
            root.left = (insertRec(root.left, info));
        else if(info.compareTo(root.info) < 0)
            root.right = (insertRec(root.right, info));
        return root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "root=" + root +
                '}';
    }
}
