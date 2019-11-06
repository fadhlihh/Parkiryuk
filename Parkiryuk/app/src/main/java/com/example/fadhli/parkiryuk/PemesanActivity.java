package com.example.fadhli.parkiryuk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import java.util.ArrayList;

public class PemesanActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pemesan_layout);

        //fragment management
        loadFragment(new ListTempatParkirFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment(android.support.v4.app.Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.home_nav:
                fragment = new ListTempatParkirFragment();
                break;
            case R.id.profile_nav:
                fragment = new ProfileFragment();
                break;
            case R.id.struk_nav:
                fragment = new StrukFragment();
                break;
            case R.id.history_nav:
                fragment = new HistoryFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
