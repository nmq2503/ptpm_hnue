package com.nmq.foodninjaver2.admin.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.adapters.ManagerUsersAdapter;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

import java.util.List;

public class AdminDetailManagerUsersActivity extends AppCompatActivity {
    ImageView ivBackBtn;
    RecyclerView recyclerViewManagerUsers;
    ManagerUsersAdapter adapter;
    AdminRepository adminRepository;
    List<UserModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_detail_manager_users);

        ivBackBtn = findViewById(R.id.ivBackBtn);
        recyclerViewManagerUsers = findViewById(R.id.recyclerViewManagerUsers);

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về AdminActivity
                Intent intent = new Intent(AdminDetailManagerUsersActivity.this, AdminActivity.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });

        // Thiết lập RecyclerView
        recyclerViewManagerUsers.setLayoutManager(new LinearLayoutManager(this));

        // Lấy dữ liệu từ cơ sở dữ liệu
        adminRepository = new AdminRepository(this);
        userList = adminRepository.getAllUsersWithRoles();

        // Kết nối Adapter
        adapter = new ManagerUsersAdapter(userList);
        recyclerViewManagerUsers.setAdapter(adapter);
    }
}