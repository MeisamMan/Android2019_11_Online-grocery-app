package org.meicode.meimall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.meicode.meimall.Models.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCartFragment extends Fragment {
    private static final String TAG = "ThirdCartFragment";

    private TextView txtPrice, txtShippingDetails;
    private Button btnBack, btnNext;
    private RadioGroup rgPaymentMethod;

    private Order incomingOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_cart, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        try {
            incomingOrder = bundle.getParcelable("order");
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (null != incomingOrder) {
            txtPrice.setText(String.valueOf(incomingOrder.getTotalPrice()));
            String finalString = "Items:\n\tAddress: " + incomingOrder.getAddress()+ "\n\t" +
                    "Email: " + incomingOrder.getEmail() + "\n\t" +
                    "Zip code: " + incomingOrder.getZipCode() + "\n\t" +
                    "Phone Number: " + incomingOrder.getPhoneNumber();
            txtShippingDetails.setText(finalString);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToPayment();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }

        return view;
    }

    private void goBack() {
        Log.d(TAG, "goBack: started");
        Order order = new Order();
        order.setTotalPrice(incomingOrder.getTotalPrice());
        order.setItems(incomingOrder.getItems());
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", order);
        CartSecondFragment cartSecondFragment = new CartSecondFragment();
        cartSecondFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.out, R.anim.in)
                .replace(R.id.fragment_container, cartSecondFragment).commit();
    }

    private void goToPayment() {
        Log.d(TAG, "goToPayment: started");
        RadioButton radioButton = getActivity().findViewById(rgPaymentMethod.getCheckedRadioButtonId());
        incomingOrder.setPaymentMethod(radioButton.getText().toString());
        incomingOrder.setSuccess(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<Order> call = retrofitClient.goToFakePayment(incomingOrder);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d(TAG, "onResponse: code: " + response.code());
                if (!response.isSuccessful()) {
                    return;
                }

                goToPaymentResult(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: t: " + t.getMessage());
            }
        });
    }

    private void goToPaymentResult(Order order) {
        Log.d(TAG, "goToPaymentResult: started");
        if (order.isSuccess()) {

            SuccessfulPaymentFragment successfulPaymentFragment = new SuccessfulPaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("order", order);
            successfulPaymentFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in, R.anim.out)
                    .replace(R.id.fragment_container, successfulPaymentFragment).commit();
        }else {


            FailedPaymentFragment failedPaymentFragment = new FailedPaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("order", order);
            failedPaymentFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in, R.anim.out)
                    .replace(R.id.fragment_container, failedPaymentFragment).commit();
        }
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");
        txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        txtShippingDetails = (TextView) view.findViewById(R.id.txtShippingDetail);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnFinish);

        rgPaymentMethod = (RadioGroup) view.findViewById(R.id.rgPaymentMethods);
    }
}
