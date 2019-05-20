package org.meicode.meimall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.meicode.meimall.Models.GroceryItem;
import org.meicode.meimall.Models.Order;

import java.util.ArrayList;

public class CartFirstFragment extends Fragment implements CartRecViewAdapter.GetTotalPrice,
        CartRecViewAdapter.DeleteCatItem {
    private static final String TAG = "CartFirstFragment";

    @Override
    public void onDeletingResult(GroceryItem item) {
        Log.d(TAG, "onDeletingResult: attempting to delete " + item.toString());
        ArrayList<Integer> itemIds = new ArrayList<>();
        itemIds.add(item.getId());
        ArrayList<GroceryItem> items = utils.getItemsById(itemIds);
        if (items.size()>0) {
            ArrayList<Integer> newItemsIds = utils.deleteCartItem(items.get(0));
            if (newItemsIds.size()==0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                recyclerView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);

            }else {
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);
                txtNoItem.setVisibility(View.GONE);
            }

            ArrayList<GroceryItem> newItems = utils.getItemsById(newItemsIds);
            this.items = newItemsIds;
            adapter.setItems(newItems);
        }
    }

    @Override
    public void onGettingTotalPriceResult(double price) {
        Log.d(TAG, "onGettingTotalPriceResult: totalPrice: " + price);
        txtPrice.setText(String.valueOf(price));
        this.totalPrice = price;
    }

    private TextView txtPrice, txtNoItem;
    private RecyclerView recyclerView;
    private Button btnNext;

    private CartRecViewAdapter adapter;

    private double totalPrice = 0;
    private ArrayList<Integer> items;

    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_cart, container, false);

        initViews(view);
        items = new ArrayList<>();

        utils = new Utils(getActivity());

        initRecView();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setTotalPrice(totalPrice);
                order.setItems(items);
                Bundle bundle = new Bundle();
                bundle.putParcelable("order", order);
                CartSecondFragment cartSecondFragment = new CartSecondFragment();
                cartSecondFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in, R.anim.out)
                        .replace(R.id.fragment_container, cartSecondFragment).commit();
            }
        });

        return view;
    }

    private void initRecView() {
        Log.d(TAG, "initRecView: started");
        adapter = new CartRecViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Utils utils = new Utils(getActivity());

        ArrayList<Integer> itemsIds = utils.getCartItems();
        if (null != itemsIds) {

            ArrayList<GroceryItem> items = utils.getItemsById(itemsIds);
            if (items.size()==0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                recyclerView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);

            }

            this.items = itemsIds;
            adapter.setItems(items);
        }else {
            btnNext.setVisibility(View.GONE);
            btnNext.setEnabled(false);
            recyclerView.setVisibility(View.GONE);
            txtNoItem.setVisibility(View.VISIBLE);
        }
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");

        txtPrice = (TextView) view.findViewById(R.id.txtSum);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        txtNoItem = (TextView) view.findViewById(R.id.txtNoItem);
    }
}
