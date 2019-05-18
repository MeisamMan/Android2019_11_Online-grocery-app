package org.meicode.meimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        initBottomNavView();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CartFirstFragment());
        transaction.commit();
    }

    private void initBottomNavView() {
        Log.d(TAG, "initBottomNavView: started");
        bottomNavigationView.setSelectedItemId(R.id.cart);;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.homeActivity:
                        Intent homeIntent = new Intent(CartActivity.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initViews() {
        Log.d(TAG, "initViews: started");
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
    }
}
