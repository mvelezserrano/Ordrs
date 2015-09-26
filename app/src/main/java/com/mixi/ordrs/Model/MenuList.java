package com.mixi.ordrs.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MenuList {
    private static MenuList sMenuList;

    private boolean menuDownloaded =false;

    private List<Dish> mDishes;

    public static MenuList get (Context context) {
        if (sMenuList == null) {
            sMenuList = new MenuList(context);
        }

        return sMenuList;
    }

    private MenuList(Context context) {
        mDishes = new ArrayList<>();
        menuDownloaded=false;
    }

    public void setDishes(List<Dish> dishes) {
        mDishes = dishes;
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

    public boolean isMenuDownloaded() {
        return menuDownloaded;
    }

    public void setMenuDownloaded(boolean menuDownloaded) {
        this.menuDownloaded = menuDownloaded;
    }
}
