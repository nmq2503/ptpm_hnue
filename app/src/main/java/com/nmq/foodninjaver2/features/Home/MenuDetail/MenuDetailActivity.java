package com.nmq.foodninjaver2.features.Home.MenuDetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.Adapter.PopularMenuAdapter;
import com.nmq.foodninjaver2.features.Home.HomeActivity;
import com.nmq.foodninjaver2.features.Home.Model.MenuDomain;

import java.util.ArrayList;

public class MenuDetailActivity extends AppCompatActivity {
    private RecyclerView rvMenuList;
    private PopularMenuAdapter adapter;
    private ArrayList<MenuDomain> menuList, originalMenuList;
    private ImageButton backBtn;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_detail);

        rvMenuList = findViewById(R.id.rvMenuList);
        rvMenuList.setLayoutManager(new LinearLayoutManager((MenuDetailActivity.this)));

        menuList = new ArrayList<>();
        menuList.add(new MenuDomain("Pepperoni pizza", "pop_1", 100.99));
        menuList.add(new MenuDomain("Cheese Burger", "pop_2", 29.99));
        menuList.add(new MenuDomain("Hotdog", "pop_3", 25.00));
        menuList.add(new MenuDomain("Drink", "nuoc_ep_xoai_dao", 20.00));
        menuList.add(new MenuDomain("Donut", "donut", 40.00));
        menuList.add(new MenuDomain("BBQ", "bbq", 150.00));

        originalMenuList = new ArrayList<>(menuList);
        adapter = new PopularMenuAdapter(menuList);
        rvMenuList.setAdapter(adapter);

        // Back
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Search
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fileList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void fileList(String string) {
        ArrayList<MenuDomain> filteredList = new ArrayList<>();
        for (MenuDomain item : originalMenuList) {
            if (item.getTitle().toLowerCase().contains(string.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

}