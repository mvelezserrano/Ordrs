package com.mixi.ordrs.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {

    private UUID mId;
    private String mName;
    private List<Dishtable> mDishes;

    public Table() {
        mId = UUID.randomUUID();
        mDishes = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void addDish (Dishtable d) {
        mDishes.add(d);
    }

    public void removeDish (int id) {
        for (Dishtable dish: mDishes) {
            if (dish.getId() == id) {
                mDishes.remove(dish);
            }
        }
    }

    public List<Dishtable> getDishes() {
        return mDishes;
    }

    public Dishtable getDish (int id) {
        for (Dishtable dish: mDishes) {
            if (dish.getId() == id) {
                return dish;
            }
        }
        return null;
    }
}
