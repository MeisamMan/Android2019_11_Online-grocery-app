package org.meicode.meimall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ShowAllCategoriesDialog extends DialogFragment {
    private static final String TAG = "ShowAllCategoriesDialog";

    public interface SelectCategory {
        void onSelectCategoryResult(String category);
    }

    private SelectCategory selectCategory;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_show_all_categories, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("All Categories");

        ListView listView = (ListView) view.findViewById(R.id.categoriesListView);
        Utils utils = new Utils(getActivity());
        final ArrayList<String> categories = utils.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);

        listView.setAdapter(adapter);

        try {
            selectCategory = (SelectCategory) getActivity();
        }catch (ClassCastException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCategory.onSelectCategoryResult(categories.get(position));
            }
        });

        return builder.create();
    }
}
