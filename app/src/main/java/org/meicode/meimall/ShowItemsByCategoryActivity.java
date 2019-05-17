package org.meicode.meimall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.meicode.meimall.Models.GroceryItem;

import java.util.ArrayList;

public class ShowItemsByCategoryActivity extends AppCompatActivity {
    private static final String TAG = "ShowItemsByCategoryActi";

    private TextView txtName;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_by_category);

        initViews();

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        try {
            Intent intent = getIntent();
            String category = intent.getStringExtra("category");
            Utils utils = new Utils(this);
            ArrayList<GroceryItem> items = utils.getItemsByCategory(category);
            adapter.setItems(items);
            txtName.setText(category);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        Log.d(TAG, "initViews: started");
        txtName = (TextView) findViewById(R.id.txtCategory);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
