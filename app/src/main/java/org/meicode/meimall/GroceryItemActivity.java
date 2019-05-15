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

    private int currentRate = 0;

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
            this.currentRate = incomingItem.getRate();
            changeVisibility(currentRate);
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
                utils.addItemToCart(incomingItem.getId());
            }
        });

        handleStarsSituation();

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

    private void handleStarsSituation() {
        Log.d(TAG, "handleStarsSituation: started");

        firstEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(1)) {
                    updateDatabase(1);
                    changeVisibility(1);
                }
            }
        });

        secondEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(2)) {
                    updateDatabase(2);
                    changeVisibility(2);
                }
            }
        });

        thirdEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(3)) {
                    updateDatabase(3);
                    changeVisibility(3);
                }
            }
        });

        firstFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(1)) {
                    updateDatabase(1);
                    changeVisibility(1);
                }
            }
        });

        secondFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(2)) {
                    updateDatabase(2);
                    changeVisibility(2);
                }
            }
        });

        thirdFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfRateHasChanged(3)) {
                    updateDatabase(3);
                    changeVisibility(3);
                }
            }
        });
    }

    private void updateDatabase(int newRate) {
        Log.d(TAG, "updateDatabase: started");
        utils.updateTheRate(incomingItem, newRate);
    }

    private void changeVisibility (int newRate) {
        Log.d(TAG, "changeVisibility: started");
        switch (newRate) {
            case 0:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.GONE);
                break;
            default:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkIfRateHasChanged (int newRate) {
        Log.d(TAG, "checkIfRateHasChanged: started");
        if (newRate == currentRate) {
            return false;
        }else {
            return true;
        }
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
