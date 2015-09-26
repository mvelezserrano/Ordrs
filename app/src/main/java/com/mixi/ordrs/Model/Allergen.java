package com.mixi.ordrs.Model;

public class Allergen {

    private int mId;
    private String mName;

    public Allergen () {}

    public Allergen(int id, String name) {
        mId = id;
        mName = name;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
