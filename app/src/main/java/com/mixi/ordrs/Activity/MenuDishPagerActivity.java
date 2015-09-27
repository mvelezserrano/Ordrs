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
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.MenuList;
import com.mixi.ordrs.R;

import java.util.List;

public class MenuDishPagerActivity extends FragmentActivity {

    private static final String EXTRA_DISH_ID = "com.mixi.ordrs.Activity.dish_id";

    private ViewPager mViewPager;
    private List<Dish> mMenuDishes;

    public static Intent newIntent(Context packageContext, int dishId) {
        Intent intent = new Intent(packageContext, MenuDishPagerActivity.class);
        intent.putExtra(EXTRA_DISH_ID, dishId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudish_pager);

        int dishId = getIntent().getIntExtra(EXTRA_DISH_ID, 0);

        mViewPager = (ViewPager) findViewById(R.id.activity_dish_pager_view_pager);

        mMenuDishes = MenuList.get(this).getDishes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Dish dish = mMenuDishes.get(position);
                return DishFragment.newInstance(dish.getId());
            }

            @Override
            public int getCount() {
                return mMenuDishes.size();
            }
        });

        for (int i = 0; i < mMenuDishes.size(); i++) {
            if (mMenuDishes.get(i).getId() == dishId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
