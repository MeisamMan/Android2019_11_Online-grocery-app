package org.meicode.meimall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.meicode.meimall.Models.GroceryItem;
import org.meicode.meimall.Models.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReviewDialog extends DialogFragment {
    private static final String TAG = "AddReviewDialog";

    private EditText edtTxtName, edtTxtReview;
    private TextView txtItemName, txtWarning;
    private Button btnAddReview;

    private int itemId = 0;

    public interface AddReview {
        void onAddReviewResult (Review review);
    }

    private AddReview addReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Review")
                .setView(view);

        initViews(view);
        Bundle bundle = getArguments();
        try {
            GroceryItem item = bundle.getParcelable("item");
            txtItemName.setText(item.getName());
            this.itemId = item.getId();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
            }
        });

        return builder.create();
    }

    private void addReview () {
        Log.d(TAG, "addReview: started");

        if (validateData()) {
            String name = edtTxtName.getText().toString();
            String reviewText = edtTxtReview.getText().toString();
            String date = getCurrentDate();

            Review review = new Review(itemId, name, date, reviewText);

            try {
                addReview = (AddReview) getActivity();

                addReview.onAddReviewResult(review);
                dismiss();
            }catch (ClassCastException e) {
                e.printStackTrace();
            }
        }else {
            txtWarning.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateData () {
        Log.d(TAG, "validateData: started");
        if (edtTxtName.getText().toString().equals("")) {
            return false;
        }
        if (edtTxtReview.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private String getCurrentDate() {
        Log.d(TAG, "getCurrentDate: called");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private void initViews (View view) {
        Log.d(TAG, "initViews: started");
        edtTxtName = (EditText) view.findViewById(R.id.edtTxtName);
        edtTxtReview = (EditText) view.findViewById(R.id.edtTxtReview);
        txtItemName = (TextView) view.findViewById(R.id.reviewName);
        txtWarning = (TextView) view.findViewById(R.id.txtWarning);
        btnAddReview = (Button) view.findViewById(R.id.btnAdd);
    }
}
