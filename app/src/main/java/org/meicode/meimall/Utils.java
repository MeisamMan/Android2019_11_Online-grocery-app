package org.meicode.meimall;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.meicode.meimall.Models.GroceryItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    public static final String DATABASE_NAME = "fake_database";

    private static int ID = 0;

    public Utils() {
    }

    public static int getId() {
        ID++;
        return ID;
    }

    public void initDatabase (Context context) {
        Log.d(TAG, "initDatabase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> possibleItems = gson.fromJson(sharedPreferences.getString("allItems", ""), type);
        if (null == possibleItems) {
            initAllItems(context);
        }
    }

    public ArrayList<GroceryItem> getAllItems (Context context) {
        Log.d(TAG, "getAllItems: started");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        return allItems;
    }

    private void initAllItems(Context context) {
        Log.d(TAG, "initAllItems: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        ArrayList<GroceryItem> allItems = new ArrayList<>();

        GroceryItem iceCream = new GroceryItem("ice cream", "produced of fresh milk",
                "https://bloximages.newyork1.vip.townnews.com/virginislandsdailynews.com/content/tncms/assets/v3/editorial/c/f9/cf961d35-5e81-58c5-ae8b-63c27b2020ef/5b57bcd072ed3.image.jpg",
                "food", 15, 2.5);

        allItems.add(new GroceryItem("cheese", "Best cheese possible",
                "https://amp.businessinsider.com/images/5b8592ba89c8a1d6218b4a36-750-563.jpg", "food", 3, 4.45));
        allItems.add(new GroceryItem("Cucumber", "it is fresh",
                "https://cdn1.medicalnewstoday.com/content/images/articles/283/283006/cucumber-slices.jpg", "vegetables", 10, 0.8));
        allItems.add(new GroceryItem("Coca cola", "it is a tasty drink",
                "https://www.myamericanmarket.com/873-large_default/coca-cola-classic.jpg", "drinks", 100, 1));
        allItems.add(new GroceryItem("Spaghetti", "it is an easy to cook meal",
                "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/barilla-rotini-pasta-1527694739.jpg","food", 16, 2.5));
        allItems.add(new GroceryItem("Chicken nugget", "usually enough for 4 person",
                "https://www.seriouseats.com/images/2014/01/20140122-taste-test-nuggets-banquet.jpg", "food", 15, 10.8));
        allItems.add(new GroceryItem("Clear Shampoo", "you won't experience hair fall with this",
                "https://100comments.com/wp-content/uploads/2018/02/Untitled-10-3.jpg", "hygiene", 42, 12.3));
        allItems.add(new GroceryItem("Axe body spray", "is hot and sweaty? not any more",
                "https://pics.drugstore.com/prodimg/519930/900.jpg", "hygiene", 9, 8.5));
        allItems.add(new GroceryItem("Kleenex", "soft and famous!",
                "https://images-na.ssl-images-amazon.com/images/I/91ZyGoGBMAL._SY355_.jpg", "hygiene", 12, 3.2));
        allItems.add(iceCream);
        String finalString = gson.toJson(allItems);
        editor.putString("allItems", finalString);
        editor.commit();
    }
}
