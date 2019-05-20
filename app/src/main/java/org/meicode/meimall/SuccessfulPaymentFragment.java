package org.meicode.meimall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.meicode.meimall.Models.GroceryItem;
import org.meicode.meimall.Models.Order;

import java.util.ArrayList;

public class SuccessfulPaymentFragment extends Fragment {
    private static final String TAG = "SuccessfulPalymentFragm";

    private Button btnGoBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_succes_payment, container, false);

        Utils utils = new Utils(getActivity());
        utils.removeCartItems();

        Bundle bundle = getArguments();
        try {
            Order order = bundle.getParcelable("order");
            ArrayList<Integer> itemIds = order.getItems();
            utils.addPopularityPoint(itemIds);
            ArrayList<GroceryItem> items = utils.getItemsById(itemIds);
            for (GroceryItem item: items) {
                ArrayList<GroceryItem> sameCategory = new ArrayList<>();
                sameCategory = utils.getItemsByCategory(item.getCategory());
                for (GroceryItem j: sameCategory) {
                    utils.increaseUserPoint(j, 4);
                }
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        btnGoBack = (Button) view.findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}
