package com.mixi.ordrs.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mixi.ordrs.Fragment.DishListFragment;
import com.mixi.ordrs.Model.Table;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.UUID;

public class DishListActivity extends SingleFragmentActivity {

    private static final String EXTRA_TABLE_ID = "com.mixi.ordrs.Activity.table_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, DishListActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID tableId = (UUID) getIntent().getSerializableExtra(EXTRA_TABLE_ID);
        Table table = TableSet.get(this).getTable(tableId);
        return DishListFragment.newInstance(table.getId());
    }

    @Override
    protected void addNewElement() {
        Toast toast = Toast.makeText(this, "Añadir Plato!!!", Toast.LENGTH_SHORT);
        toast.show();
        DishListFragment fragment = (DishListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.addDish();
    }
}
