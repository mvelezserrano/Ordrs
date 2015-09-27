package com.mixi.ordrs.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.mixi.ordrs.Fragment.DishFragment;
import com.mixi.ordrs.Model.Dishtable;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.List;
import java.util.UUID;

public class TableDishPagerActivity extends FragmentActivity {

    private static final String EXTRA_DISH_ID = "com.mixi.ordrs.Activity.dish_id";
    private static final String EXTRA_TABLE_ID = "com.mixi.ordrs.Activity.table_id";

    private ViewPager mViewPager;
    private List<Dishtable> mTableDishes;
    private UUID mTableId;

    public static Intent newIntent(Context packageContext, UUID tableId, int dishId) {
        Intent intent = new Intent(packageContext, TableDishPagerActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, tableId);
        intent.putExtra(EXTRA_DISH_ID, dishId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudish_pager);

        mTableId = (UUID) getIntent().getSerializableExtra(EXTRA_TABLE_ID);
        final int dishId = getIntent().getIntExtra(EXTRA_DISH_ID, 0);

        mViewPager = (ViewPager) findViewById(R.id.activity_dish_pager_view_pager);

        mTableDishes = TableSet.get(this).getTable(mTableId).getDishes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Dishtable dish = mTableDishes.get(position);
                return DishFragment.newInstance(dish.getRequest(), dish.getId());
            }

            @Override
            public int getCount() {
                return mTableDishes.size();
            }
        });

        for (int i = 0; i < mTableDishes.size(); i++) {
            if (mTableDishes.get(i).getId() == dishId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
