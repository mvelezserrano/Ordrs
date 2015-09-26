package com.mixi.ordrs.Activity;

import android.support.v4.app.Fragment;

import com.mixi.ordrs.Fragment.MenuListFragment;

public class MenuListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MenuListFragment();
    }

    @Override
    protected void addNewElement() {

    }
}
