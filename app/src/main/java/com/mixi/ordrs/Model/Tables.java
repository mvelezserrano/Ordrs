package com.mixi.ordrs.Model;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tables {
    private static Tables sTables;

    private List<Table> mTables;

    public static Tables get (Context context) {
        if (sTables == null) {
            sTables = new Tables(context);
        }

        return sTables;
    }

    private Tables (Context context) {
        mTables = new ArrayList<>();
    }

    public void addTable (Table t) {
        mTables.add(t);
    }

    public List<Table> getTables() {
        return mTables;
    }

    public Table getTable (UUID id) {
        for (Table table: mTables) {
            if (table.getId().equals(id)) {
                return table;
            }
        }
        return null;
    }
}
