package com.mixi.ordrs.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mixi.ordrs.Fragment.DishFragment;
import com.mixi.ordrs.Fragment.DishListFragment;
import com.mixi.ordrs.Fragment.TableListFragment;
import com.mixi.ordrs.Model.Table;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.UUID;

public class DishListActivity extends SingleFragmentActivity {

    private static final String EXTRA_TABLE_ID = "com.mixi.ordrs.Activity.table_id";
    private static final int REQUEST_MENU_DISH = 1;
    public static final int REQUEST_TABLE_DISH = 2;

    public static Intent newIntent(Context packageContext, UUID tableId) {
        Intent intent = new Intent(packageContext, DishListActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, tableId);
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
        Intent intent = new Intent(this, MenuListActivity.class);
        startActivityForResult(intent, REQUEST_MENU_DISH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Entramos en el onActivyResult de DishListActivity");
        if (requestCode == REQUEST_MENU_DISH) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("Seleccionamos un plato!!!");
                DishListFragment fragment = (DishListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.addDish(
                        data.getExtras().getInt(DishFragment.EXTRA_SELECTED_DISH_ID),
                        data.getExtras().getString(DishFragment.EXTRA_REQUESTS));
            }
        } else if (requestCode == REQUEST_TABLE_DISH) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("Borramos plato???");
                DishListFragment fragment = (DishListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.removeDish(data.getExtras().getInt(DishFragment.EXTRA_SELECTED_DISH_ID));
            }
        }
    }
}
