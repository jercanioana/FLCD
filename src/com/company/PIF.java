package com.company;

import java.util.ArrayList;
import java.util.HashMap;



public class PIF {
    private ArrayList<Pair<String, Integer>> pif;

    public PIF (){
        this.pif = new ArrayList<>();
    }

    public void genPIF(String token, Integer pos){
        Pair p = new Pair(token, pos);
        pif.add(p);
    }

    public Integer pos(String token, SymbolTable st){
        Node node = st.findNode(st.root, token);
        if(node != null){
            return node.position;
        }else{
            st.insertNode(token);
            return st.findNode(st.root, token).position;
        }
    }
}
