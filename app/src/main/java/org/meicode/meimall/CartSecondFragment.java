package org.meicode.meimall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.meicode.meimall.Models.Order;

public class CartSecondFragment extends Fragment {
    private static final String TAG = "CartSecondFragment";

    private EditText edtTxtAddress, edtTxtZipCode, edtTxtPhoneNumber, edtTxtEmail;
    private Button btnBack, btnNext;

    private Order incomingOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frafment_second_cart, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            incomingOrder = bundle.getParcelable("order");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CartFirstFragment()).commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    passData();
                }
            }
        });

        return view;
    }

    private void passData() {
        Log.d(TAG, "passData: started");

        Bundle bundle = new Bundle();
        bundle.putParcelable("order", incomingOrder);
    }

    private boolean validateData () {
        Log.d(TAG, "validateData: started");
        if (edtTxtAddress.getText().toString().equals("")) {
            return false;
        }
        if (edtTxtEmail.getText().toString().equals("")) {
            return false;
        }

        if (edtTxtZipCode.getText().toString().equals("")) {
            return false;
        }

        if (edtTxtPhoneNumber.getText().toString().equals("")) {
            return false;
        }

        return true;
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");

        edtTxtAddress = (EditText) view.findViewById(R.id.edtTxtAddress);
        edtTxtZipCode = (EditText) view.findViewById(R.id.edtTxtZipCode);
        edtTxtPhoneNumber = (EditText) view.findViewById(R.id.edtTxtPhoneNumber);
        edtTxtEmail = (EditText) view.findViewById(R.id.edtTxtEmail);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnNext);
    }
}
