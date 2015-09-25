package com.mixi.ordrs.Activity;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mixi.ordrs.Fragment.TableListFragment;
import com.mixi.ordrs.R;

public class TableListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TableListFragment();
    }

    @Override
    protected void addNewElement() {
        Toast toast = Toast.makeText(this, "Añadir Mesa!!!", Toast.LENGTH_SHORT);
        toast.show();
        TableListFragment fragment = (TableListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.addTable();
    }
}
