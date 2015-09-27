package com.mixi.ordrs.Model;

public class Dishtable extends Dish {

    private String mRequest;

    public Dishtable(Dish dishToCopy) {
        super(dishToCopy.getId(), dishToCopy.getName(), dishToCopy.getPrice(), dishToCopy.getImageUrl(), dishToCopy.getIngredients(), dishToCopy.getAllergens());
    }

    public String getRequest() {
        return mRequest;
    }

    public void setRequest(String request) {
        mRequest = request;
    }
}
