package com.company.module.entities;

import java.util.ArrayList;

public class Production {
    private String first;
    private ArrayList<String> second;

    public Production(String first, ArrayList<String> second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public ArrayList<String> getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Production{" +
                "first='" + first + '\'' +
                ", second=" + second +
                '}';
    }
}
