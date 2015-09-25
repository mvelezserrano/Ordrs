package com.mixi.ordrs.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static Menu sMenu;

    private List<Dish> mDishes;

    public static Menu get (Context context) {
        if (sMenu == null) {
            sMenu = new Menu(context);
        }

        return sMenu;
    }

    private Menu (Context context) {
        mDishes = new ArrayList<>();
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
