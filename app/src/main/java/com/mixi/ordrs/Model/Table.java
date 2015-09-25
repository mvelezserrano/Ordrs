package com.mixi.ordrs.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {

    private UUID mId;
    private String mName;
    private List<Dish> mDishes;

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

    public void addDish (Dish d) {
        mDishes.add(d);
    }

    public List<Dish> getDishes() {
        return mDishes;
    }

    public Dish getDish (int id) {
        for (Dish dish: mDishes) {
            if (dish.getId() == id) {
                return dish;
            }
        }
        return null;
    }
}
