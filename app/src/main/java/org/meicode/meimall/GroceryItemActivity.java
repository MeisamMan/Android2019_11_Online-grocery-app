package org.meicode.meimall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroceryItemActivity extends AppCompatActivity {
    private static final String TAG = "GroceryItemActivity";

    private TextView txtName, txtPrice, txtDescription, txtAvailability;
    private ImageView itemImage;
    private Button btnAddToCart;

    private ImageView firstFilledStar, secondFilledStar, thirdFilledStar, firstEmptyStar, secondEmptyStar, thirdEmptyStar;
    private RecyclerView reviewsRecView;

    private RelativeLayout addReviewRelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();
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
