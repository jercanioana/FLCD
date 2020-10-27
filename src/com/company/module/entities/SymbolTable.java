package com.company.module.entities;

import com.company.module.entities.Node;

public class SymbolTable {
    public Node root;
    public SymbolTable(){
        root = null;
    }

    public SymbolTable(Node root) {
        this.root = root;
    }

    public Node findNode(Node root, String info){
        //tree is empty or we have the info at the root
        if (root==null || root.info.compareTo(info) == 0)
            return root;

        if (root.info.compareTo(info) > 0)
            return findNode(root.left, info);

        return findNode(root.right, info);
    }

    public void insertNode(String info){
        this.root = this.insertRec(root, info);
    }

    public Node insertRec(Node root, String info){
        if (root == null){
            root = new Node(info);
            return root;
        }
        if(info.compareTo(root.info) < 0)
            root.left = (insertRec(root.left, info));
        else if(info.compareTo(root.info) > 0)
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
