package com.company;

public class Pair<K,V> {
    private String K;
    private Integer V;

    public Pair(String k, Integer v) {
        K = k;
        V = v;
    }

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public Integer getV() {
        return V;
    }

    public void setV(Integer v) {
        V = v;
    }
}
