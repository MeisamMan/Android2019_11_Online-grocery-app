package org.meicode.meimall;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.meicode.meimall.Models.GroceryItem;
import org.meicode.meimall.Models.Review;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    public static final String DATABASE_NAME = "fake_database";

    private static int ID = 0;
    private static int ORDER_ID = 0;

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static int getOrderId () {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getId() {
        ID++;
        return ID;
    }

    public void addItemToCart(int id) {
        Log.d(TAG, "addItemToCart: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems", null), type);
        if (null == cartItems) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(id);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartItems", gson.toJson(cartItems));
        editor.commit();
    }

    public void updateTheRate(GroceryItem item, int newRate) {
        Log.d(TAG, "updateTheRate: started for item: " + item.getName());
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        if (null != items) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : items) {
                if (i.getId() == item.getId()) {
                    i.setRate(newRate);
                }
                newItems.add(i);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems", gson.toJson(newItems));
            editor.commit();
        }
    }

    public boolean addReview(Review review) {
        Log.d(TAG, "addReview: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        if (null != items) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem item : items) {
                if (item.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                }

                newItems.add(item);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems", gson.toJson(newItems));
            editor.commit();
            return true;
        }

        return false;
    }

    public ArrayList<Review> getReviewForItem(int id) {
        Log.d(TAG, "getReviewForItem: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        if (null != items) {
            for (GroceryItem item : items) {
                if (item.getId() == id) {
                    return item.getReviews();
                }
            }
        }
        return null;
    }

    public void initDatabase() {
        Log.d(TAG, "initDatabase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> possibleItems = gson.fromJson(sharedPreferences.getString("allItems", ""), type);
        if (null == possibleItems) {
            initAllItems();
        }
    }

    public ArrayList<GroceryItem> getAllItems() {
        Log.d(TAG, "getAllItems: started");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        return allItems;
    }

    private void initAllItems() {
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
                "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/barilla-rotini-pasta-1527694739.jpg", "food", 16, 2.5));
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

    public ArrayList<GroceryItem> searchForItem(String text) {
        Log.d(TAG, "searchForItem: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);

        ArrayList<GroceryItem> searchItems = new ArrayList<>();
        if (null != allItems) {
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    searchItems.add(item);
                }

                String[] splittedString = item.getName().split(" ");
                for (int i = 0; i < splittedString.length; i++) {
                    if (splittedString[i].equalsIgnoreCase(text)) {

                        boolean doesExist = false;
                        for (GroceryItem searchItem : searchItems) {
                            if (searchItem.equals(item)) {
                                doesExist = true;
                            }
                        }

                        if (!doesExist) {
                            searchItems.add(item);
                        }
                    }
                }
            }
        }

        return searchItems;
    }

    public ArrayList<String> getThreeCategories() {
        Log.d(TAG, "getThreeCategories: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        ArrayList<String> categories = new ArrayList<>();

        if (null != allItems) {
            for (int i = 0; i < allItems.size(); i++) {
                if (categories.size() < 3) {
                    boolean doesExist = false;
                    for (String s : categories) {
                        if (allItems.get(i).getCategory().equals(s)) {
                            doesExist = true;
                        }
                    }

                    if (!doesExist) {
                        categories.add(allItems.get(i).getCategory());
                    }
                }
            }
        }

        return categories;
    }

    public ArrayList<String> getAllCategories() {
        Log.d(TAG, "getAllCategories: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        ArrayList<String> categories = new ArrayList<>();

        if (null != allItems) {
            for (int i = 0; i < allItems.size(); i++) {

                boolean doesExist = false;
                for (String s : categories) {
                    if (allItems.get(i).getCategory().equals(s)) {
                        doesExist = true;
                    }
                }

                if (!doesExist) {
                    categories.add(allItems.get(i).getCategory());
                }

            }
        }

        return categories;
    }

    public ArrayList<GroceryItem> getItemsByCategory (String category) {
        Log.d(TAG, "getItemsByCategory: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);

        ArrayList<GroceryItem> newItems = new ArrayList<>();
        if (null != allItems) {
            for (GroceryItem item: allItems) {
                if (item.getCategory().equals(category)) {
                    newItems.add(item);
                }
            }
        }
        return newItems;
    }

    public ArrayList<Integer> getCartItems () {
        Log.d(TAG, "getCartItems: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems", null), type);
        return cartItems;
    }

    public ArrayList<GroceryItem> getItemsById (ArrayList<Integer> ids) {
        Log.d(TAG, "getItemsById: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);

        ArrayList<GroceryItem> resultItems = new ArrayList<>();

        for (int id: ids) {
            if (null != allItems) {
                for (GroceryItem item: allItems) {
                    if (item.getId() == id) {
                        resultItems.add(item);
                    }
                }
            }
        }

        return resultItems;
    }

    public ArrayList<Integer> deleteCartItem (GroceryItem item) {
        Log.d(TAG, "deleteCartItem: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems", null), type);

        ArrayList<Integer> newItems = new ArrayList<>();

        if (null != cartItems) {
            for (int i: cartItems) {
                if (item.getId() != i) {
                    newItems.add(i);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cartItems", gson.toJson(newItems));
            editor.commit();
        }

        return newItems;
    }
}
