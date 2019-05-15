package org.meicode.meimall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.meicode.meimall.Models.GroceryItem;
import org.meicode.meimall.Models.Review;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryItemActivity";

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: we are adding " + review.toString());

        utils.addReview(review);
        ArrayList<Review> reviews = utils.getReviewForItem(review.getGroceryItemId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }
    }

    private TextView txtName, txtPrice, txtDescription, txtAvailability;
    private ImageView itemImage;
    private Button btnAddToCart;

    private ImageView firstFilledStar, secondFilledStar, thirdFilledStar, firstEmptyStar, secondEmptyStar, thirdEmptyStar;
    private RecyclerView reviewsRecView;

    private ReviewsAdapter adapter;

    private RelativeLayout addReviewRelLayout;

    private GroceryItem incomingItem;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        utils = new Utils(this);

        initViews();

        Intent intent = getIntent();
        try {
            incomingItem = intent.getParcelableExtra("item");
            setViewsValues();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * responsible for setting the initial values for views
     */
    private void setViewsValues () {
        Log.d(TAG, "setViewsValues: started");
        txtName.setText(incomingItem.getName());
        txtPrice.setText(String.valueOf(incomingItem.getPrice() + "$"));
        txtAvailability.setText(incomingItem.getAvailableAmount() + " number(s) available");
        txtDescription.setText(incomingItem.getDescription());

        Glide.with(this)
                .asBitmap()
                .load(incomingItem.getImageUrl())
                .into(itemImage);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add item to the cart
            }
        });

        //TODO: handle the star situation

        adapter = new ReviewsAdapter();
        reviewsRecView.setAdapter(adapter);
        reviewsRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> reviews = utils.getReviewForItem(incomingItem.getId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }

        addReviewRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show dialog

                AddReviewDialog addReviewDialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("item", incomingItem);
                addReviewDialog.setArguments(bundle);
                addReviewDialog.show(getSupportFragmentManager(), "add review dialog");
            }
        });
    }

    private void initViews () {
        Log.d(TAG, "initViews: started");

        txtName = (TextView) findViewById(R.id.txtName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDescription = (TextView) findViewById(R.id.txtDesc);
        txtAvailability = (TextView) findViewById(R.id.txtAvailability);

        itemImage = (ImageView) findViewById(R.id.itemImage);

        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);

        firstFilledStar = (ImageView) findViewById(R.id.firstFilledStar);
        secondFilledStar = (ImageView) findViewById(R.id.secondFilledStar);
        thirdFilledStar = (ImageView) findViewById(R.id.thirdFilledStar);
        firstEmptyStar = (ImageView) findViewById(R.id.firstEmptyStar);
        secondEmptyStar = (ImageView) findViewById(R.id.secondEmptyStar);
        thirdEmptyStar = (ImageView) findViewById(R.id.thirdEmptyStar);

        reviewsRecView = (RecyclerView) findViewById(R.id.reviewsRecView);

        addReviewRelLayout = (RelativeLayout) findViewById(R.id.addReviewRelLayout);
    }
}
