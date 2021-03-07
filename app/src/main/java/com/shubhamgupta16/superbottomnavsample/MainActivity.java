package com.shubhamgupta16.superbottomnavsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.shubhamgupta16.superbottomnav.SuperBottomNavigation;

public class MainActivity extends AppCompatActivity {

    private SuperBottomNavigation superBottomNav;
    public static final String TAG = "SuperBottomNav";

    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superBottomNav = findViewById(R.id.superBottomNav);

        superBottomNav.setOnItemSelectChangeListener(new SuperBottomNavigation.OnItemSelectChangeListener() {
            @Override
            public void onChange(int id, int position) {
                Log.d(TAG, "id: " + id + ", position: " + position);
            }
        });

    }

    public void addOneMoreToCart(View view) {
        cartCount++;
        superBottomNav.setBadge(R.id.action_cart, cartCount, true);
    }

    public void removeAllFromCart(View view) {
        cartCount = 0;
        superBottomNav.setBadgeToPosition(3, cartCount, true);
    }
}