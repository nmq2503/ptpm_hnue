package com.nmq.foodninjaver2.features.cart.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.cart.Adapter.Item;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends Activity {
    public static final int MAX_ORDER_LIMIT = 10000;
    public static int[] imgArr = {
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,

    };

    private SharedPreferences sharedPreferences;
    private Intent intent;

    private TextView itemNameView;
    private ImageView itemImageView;
    private EditText itemOrderNumberView;
    private Button orderButton;
    private Button resetButton;

    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);

        initializeViews();
        populateItemDetails();
        setupClickListeners();
    }

    /**
     * Initialize views from the layout.
     */
    private void initializeViews() {
        itemNameView = findViewById(R.id.detail_item_name);
        itemImageView = findViewById(R.id.detail_item_img);
        itemOrderNumberView = findViewById(R.id.detail_item_ord_number);
        orderButton = findViewById(R.id.detail_order);
        resetButton = findViewById(R.id.detail_reset);

        // Create item object from intent data
        currentItem = new Item(
                intent.getIntExtra("iItemSeqNum", 0),
                intent.getStringExtra("strItemName"),
                intent.getDoubleExtra("dItemPrice", 0),
                intent.getIntExtra("iItemOrdNumber", 0),
                intent.getIntExtra("iItemImgId", R.drawable.img)
        );
    }

    /**
     * Populate item details to the views.
     */
    private void populateItemDetails() {
        itemNameView.setText(currentItem.strItemName);
        itemImageView.setImageResource(currentItem.iItemImgId);
        itemOrderNumberView.setText(String.valueOf(1)); // Default value
    }

    /**
     * Set up click listeners for the buttons.
     */
    private void setupClickListeners() {
        orderButton.setOnClickListener(view -> {
            String input = itemOrderNumberView.getText().toString();
            if (input.isEmpty() || !input.matches("\\d+")) {
                Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_SHORT).show();
                return;
            }

            int orderNumber = Integer.parseInt(input);
            if (processOrder(currentItem, orderNumber)) {
                onBackPressed();
            }
        });

        resetButton.setOnClickListener(view -> {
            resetOrderList();
            onBackPressed();
        });
    }

    /**
     * Process the order for the current item.
     *
     * @param item       The item to order.
     * @param orderCount The number of items to order.
     * @return True if the order was successful, false otherwise.
     */
    private boolean processOrder(Item item, int orderCount) {
        if (orderCount > MAX_ORDER_LIMIT) {
            Toast.makeText(this, "The number should be no more than " + MAX_ORDER_LIMIT, Toast.LENGTH_SHORT).show();
            return false;
        }

        // Update in MenuListFragment
        for (Item listItem : MenuListFragment.itemList) {
            if (listItem.strItemName.equals(item.strItemName)) {
                int newOrderCount = listItem.iItemOrdNum + orderCount;
                if (newOrderCount > MAX_ORDER_LIMIT) {
                    Toast.makeText(this, "The number should be no more than " + MAX_ORDER_LIMIT, Toast.LENGTH_SHORT).show();
                    return false;
                }

                listItem.setItemOrdNum(newOrderCount);
                saveToSharedPreference("itemList", MenuListFragment.itemList);
                break;
            }
        }

        // Update in MenuCartFragment
        for (Item cartItem : MenuCartFragment.itemOrderedList) {
            if (cartItem.strItemName.equals(item.strItemName)) {
                cartItem.setItemOrdNum(cartItem.iItemOrdNum + orderCount);
                return true;
            }
        }

        // Add new item to cart if not already in the list
        item.setItemOrdNum(orderCount);
        MenuCartFragment.itemOrderedList.add(0, item);
        return true;
    }

    /**
     * Reset all orders in both MenuListFragment and MenuCartFragment.
     */
    private void resetOrderList() {
        resetMenuList();
        MenuCartFragment.itemOrderedList.clear();
    }

    /**
     * Reset the menu list in MenuListFragment.
     */
    private void resetMenuList() {
        MenuListFragment.itemList.clear();
        for (int i = 0; i < imgArr.length; i++) {
            MenuListFragment.itemList.add(new Item(i + 1, "Food #" + (i + 1), 10.0 * (i + 1), 0, imgArr[i]));
        }
        saveToSharedPreference("itemList", MenuListFragment.itemList);
    }

    /**
     * Save the item list to SharedPreferences.
     */
    private void saveToSharedPreference(String key, List<Item> list) {
        StringBuilder sb = new StringBuilder();
        for (Item item : list) {
            sb.append(item.iItemOrdNum).append('-');
        }
        sharedPreferences.edit().putString(key, sb.toString()).apply(); // Use apply for async saving
    }
}
