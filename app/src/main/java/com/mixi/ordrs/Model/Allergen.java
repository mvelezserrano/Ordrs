package com.mixi.ordrs.Model;

public class Allergen {

    private int mId;
    private String mName;

    public Allergen(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
