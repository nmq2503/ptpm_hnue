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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
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
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_detail);

        rvMenuList = findViewById(R.id.rvMenuList);
        rvMenuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        edtSearch = findViewById(R.id.edtSearch);
        backBtn = findViewById(R.id.backBtn);

        dataBaseHelper = new DataBaseHelper(this);

        menuList = dataBaseHelper.getAllMenuItems();
        originalMenuList = new ArrayList<>(menuList);

        setupRecyclerView();

        // Back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Search
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

    private void setupRecyclerView() {
        adapter = new PopularMenuAdapter(menuList);
        rvMenuList.setAdapter(adapter);
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