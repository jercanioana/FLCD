package com.company.module.entities;

import com.company.module.entities.Node;
import com.company.module.entities.Pair;
import com.company.module.entities.SymbolTable;

import java.util.ArrayList;


public class PIF {
    private ArrayList<Pair<String, Integer>> pif;

    public PIF (){
        this.pif = new ArrayList<>();
    }

    public void genPIF(String token, Integer pos){
        Pair p = new Pair<>(token, pos);
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

    public int getSize(){
        return pif.size();
    }

    public Pair getElement(int index){
        return pif.get(index);
    }
}
