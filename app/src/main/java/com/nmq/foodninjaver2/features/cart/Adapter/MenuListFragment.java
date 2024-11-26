package com.nmq.foodninjaver2.features.cart.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.cart.Adapter.Item;
import com.nmq.foodninjaver2.features.cart.Adapter.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuListFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private ItemAdapter menuAdapter;
    public static List<Item> itemList = new ArrayList<>();
    private ListView menuListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);

        initItemList();

        // Inflate layout for fragment
        View rootView = inflater.inflate(R.layout.test, container, false);

        menuListView = rootView.findViewById(R.id.menu_list);
        menuAdapter = new ItemAdapter(requireActivity(), R.layout.test, itemList, true);
        menuListView.setAdapter(menuAdapter);

        menuListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Item item = menuAdapter.getItem(position);

            if (item == null) return;

            // Start DetailActivity and pass item data
            Intent intent = new Intent(requireActivity(), DetailActivity.class);
            intent.putExtra("iItemSeqNum", item.iItemSeqNum);
            intent.putExtra("strItemName", item.strItemName);
            intent.putExtra("dItemPrice", item.dItemPrice);
            intent.putExtra("iItemOrdNumber", item.iItemOrdNum);
            intent.putExtra("iItemImgId", item.iItemImgId);
            startActivity(intent);
        });

        return rootView;
    }

    /**
     * Initialize the item list. If data exists in SharedPreferences, it is loaded.
     * Otherwise, default data is created and saved.
     */
    private void initItemList() {
        itemList.clear();
        int[] savedData = parseFromSharedPreference(sharedPreferences);

        if (savedData != null) {
            for (int i = 0; i < savedData.length; i++) {
                itemList.add(new Item(i + 1, "Food #" + (i + 1), 10.0 * (i + 1), savedData[i], DetailActivity.imgArr[i]));
            }
        } else {
            // Default initialization
            for (int i = 0; i < 10; i++) {
                itemList.add(new Item(i + 1, "Food #" + (i + 1), 10.0 * (i + 1), 0, DetailActivity.imgArr[i]));
            }
            saveToSharedPreference(itemList);
        }
    }

    /**
     * Parse the saved data from SharedPreferences and return it as an int array.
     */
    private int[] parseFromSharedPreference(SharedPreferences sharedPreferences) {
        String savedString = sharedPreferences.getString("itemList", null);
        if (savedString == null || savedString.isEmpty()) return null;

        String[] split = savedString.split("-");
        int[] parsedArray = new int[split.length];

        try {
            for (int i = 0; i < split.length; i++) {
                parsedArray[i] = Integer.parseInt(split[i]);
            }
        } catch (NumberFormatException e) {
            return null; // Handle invalid format gracefully
        }

        return parsedArray;
    }

    /**
     * Save the item list to SharedPreferences.
     */
    private void saveToSharedPreference(List<Item> itemList) {
        StringBuilder sb = new StringBuilder();
        for (Item item : itemList) {
            sb.append(item.iItemOrdNum).append("-");
        }
        // Save asynchronously
        sharedPreferences.edit().putString("itemList", sb.toString()).apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        menuAdapter.notifyDataSetChanged();
        saveToSharedPreference(itemList);
    }
}
