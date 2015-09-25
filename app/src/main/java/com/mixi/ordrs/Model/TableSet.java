package com.mixi.ordrs.Model;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TableSet {
    private static TableSet sTableSet;

    private List<Table> mTables;

    public static TableSet get (Context context) {
        if (sTableSet == null) {
            sTableSet = new TableSet(context);
        }

        return sTableSet;
    }

    private TableSet(Context context) {
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
