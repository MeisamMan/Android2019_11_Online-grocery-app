package org.meicode.meimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.meicode.meimall.Models.GroceryItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ShowAllCategoriesDialog.SelectCategory {
    private static final String TAG = "SearchActivity";

    @Override
    public void onSelectCategoryResult(String category) {
        Log.d(TAG, "onSelectCategoryResult: category: " + category);
        Intent intent = new Intent(this, ShowItemsByCategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private EditText searchBar;
    private ImageView btnSearch;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private TextView txtFirstCat, txtSecondCat, txtThirdCat, txtSeeAllCategories;

    private GroceryItemAdapter adapter;

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        utils = new Utils(this);
        adapter = new GroceryItemAdapter(this);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initBottomNavigation();
        initThreeTextViews();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });


        txtSeeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog();
                showAllCategoriesDialog.show(getSupportFragmentManager(), "all dialog");
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<GroceryItem> items = utils.searchForItem(String.valueOf(s));
                adapter.setItems(items);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initThreeTextViews() {
        Log.d(TAG, "initThreeTextViews: started");
        ArrayList<String> categories = utils.getThreeCategories();
        switch (categories.size()) {
            case 1:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setVisibility(View.GONE);
                txtThirdCat.setVisibility(View.GONE);
                break;
            case 2:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setText(categories.get(1));
                txtThirdCat.setVisibility(View.GONE);
                break;
            case 3:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setText(categories.get(1));
                txtThirdCat.setText(categories.get(2));
                break;
            default:
                break;

        }

        txtFirstCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category", txtFirstCat.getText().toString());
                startActivity(intent);
            }
        });

        txtSecondCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category", txtSecondCat.getText().toString());
                startActivity(intent);
            }
        });

        txtThirdCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category", txtThirdCat.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initiateSearch() {
        Log.d(TAG, "initiateSearch: started");

        String text = searchBar.getText().toString();
        ArrayList<GroceryItem> items = utils.searchForItem(text);
        for (GroceryItem item: items) {
            utils.increaseUserPoint(item, 3);
        }
        adapter.setItems(items);
    }

    private void initViews () {
        Log.d(TAG, "initViews: started");
        searchBar = (EditText) findViewById(R.id.edtTxtSearchBar);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        txtFirstCat = (TextView) findViewById(R.id.firstCategory);
        txtSecondCat = (TextView) findViewById(R.id.secondCategory);
        txtThirdCat = (TextView) findViewById(R.id.thirdCategory);
        txtSeeAllCategories = (TextView) findViewById(R.id.btnAllCategories);
    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: started");
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        break;
                    case R.id.homeActivity:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
