package com.nmq.foodninjaver2.admin.views.manager_users;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.adapters.ManagerUsersAdapter;
import com.nmq.foodninjaver2.admin.views.AdminActivity;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

import java.io.File;
import java.util.List;

public class AdminDetailManagerUsersActivity extends AppCompatActivity {
    ImageView ivBackBtn;
    RecyclerView recyclerViewManagerUsers;
    ManagerUsersAdapter adapter;
    AdminRepository adminRepository;
    List<UserModel> userList;

    Button btnAdd;
    Button btnEdit;
    Button btnDelete;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_detail_manager_users);

        ivBackBtn = findViewById(R.id.ivBackBtn);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerViewManagerUsers = findViewById(R.id.recyclerViewManagerUsers);
        searchView = findViewById(R.id.searchView);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false; // Không xử lý khi submit
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()) {
//                    // Hiển thị lại danh sách đầy đủ
//                    updateUsers();
//                } else {
//                    // Lọc danh sách
//                    adapter.filter(newText);
//                }
//                return true;
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDetailManagerUsersActivity.this, AdminAddUserActivity.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel selectedUser = adapter.getSelectedUser();
                if (selectedUser != null) {
                    editSelectedUser(selectedUser);
                } else {
                    Toast.makeText(AdminDetailManagerUsersActivity.this, "Vui lòng chọn người dùng cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            UserModel selectedUser = adapter.getSelectedUser();
            if (selectedUser != null) {
                // Gọi hàm xác nhận xóa
                confirmDeleteUser(selectedUser);
            } else {
                Toast.makeText(AdminDetailManagerUsersActivity.this, "Vui lòng chọn người dùng cần xóa", Toast.LENGTH_SHORT).show();
            }
        });


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

    private void editSelectedUser(UserModel selectedUser) {
        if (selectedUser != null) {
            // Tạo Intent để chuyển sang màn hình AdminEditUserActivity
            Intent intent = new Intent(AdminDetailManagerUsersActivity.this, AdminEditUserActivity.class);

            // Truyền dữ liệu qua Intent (sử dụng putExtra)
            intent.putExtra("user_id", selectedUser.getUserId());
            intent.putExtra("user_name", selectedUser.getUserName());
            intent.putExtra("email", selectedUser.getEmail());
            intent.putExtra("password", selectedUser.getPassword());
            intent.putExtra("first_name", selectedUser.getFirstName());
            intent.putExtra("last_name", selectedUser.getLastName());
            intent.putExtra("phone_number", selectedUser.getPhoneNumber());
            intent.putExtra("address", selectedUser.getAddress());
            intent.putExtra("date_of_birth", selectedUser.getDateOfBirth());
            intent.putExtra("url_image_profile", selectedUser.getUrlImageProfile());
            intent.putExtra("role_id", selectedUser.getRoleId());

            // Bắt đầu Activity AdminEditUserActivity
            startActivity(intent);
        }
    }

//    private void confirmDeleteUser(UserModel selectedUser) {
//        new AlertDialog.Builder(AdminDetailManagerUsersActivity.this)
//                .setTitle("Xác nhận xóa")
//                .setMessage("Bạn có chắc chắn muốn xóa người dùng này không?")
//                .setPositiveButton("Xóa", (dialog, which) -> {
//                    // Xóa dữ liệu khỏi database
//                    Log.d("DeleteUser", "Id user: " + selectedUser.getUserId());
//                    boolean isDelete = adminRepository.deleteUserById(selectedUser.getUserId());
//
//                    if (isDelete) {
//                        // Lấy đường dẫn tệp ảnh từ URL
//                        String imagePath = selectedUser.getUrlImageProfile();
//
//                        // Kiểm tra xem đường dẫn ảnh có hợp lệ và không phải null
//                        if (imagePath != null && !imagePath.isEmpty()) {
//                            File imageFile = new File(imagePath);
//
//                            // Kiểm tra xem tệp ảnh có tồn tại không
//                            if (imageFile.exists()) {
//                                boolean isDeleted = imageFile.delete();
//
//                                // Xóa ảnh nếu tồn tại
//                                if (isDeleted) {
//                                    Log.d("DeleteUser", "Ảnh đã được xóa thành công.");
//                                } else {
//                                    Log.d("DeleteUser", "Không thể xóa ảnh.");
//                                }
//                            } else {
//                                // Nếu ảnh không tồn tại, bỏ qua
//                                Log.d("DeleteUser", "Ảnh không tồn tại: " + imagePath);
//                            }
//                        } else {
//                            // Đường dẫn ảnh không hợp lệ (null hoặc trống)
//                            Log.d("DeleteUser", "Đường dẫn ảnh không hợp lệ.");
//                        }
//
//                        // Cập nhật danh sách và RecyclerView
////                        userList.remove(selectedUser);
////                        adapter.updateData(userList);
//
//                        updateUsers();
//
//                        Toast.makeText(AdminDetailManagerUsersActivity.this, "Đã xóa người dùng", Toast.LENGTH_SHORT).show();
//                        adapter.clearSelection();
//                    } else {
//                        Toast.makeText(AdminDetailManagerUsersActivity.this, "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
//                        adapter.clearSelection();
//                    }
//                })
//                .setNegativeButton("Hủy", (dialog, which) -> {
//                    // Đặt lại trạng thái chọn
//                    adapter.clearSelection();
//                })
//                .show();
//    }

    private void confirmDeleteUser(UserModel selectedUser) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                adapter.clearSelection();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDelete = adminRepository.deleteUserById(selectedUser.getUserId());
                if (isDelete) {
                    // Lấy đường dẫn tệp ảnh từ URL
                    String imagePath = selectedUser.getUrlImageProfile();

                    // Kiểm tra xem đường dẫn ảnh có hợp lệ và không phải null
                    if (imagePath != null && !imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);

                        // Kiểm tra xem tệp ảnh có tồn tại không
                        if (imageFile.exists()) {
                            boolean isDeleted = imageFile.delete();

                            // Xóa ảnh nếu tồn tại
                            if (isDeleted) {
                                Log.d("DeleteUser", "Ảnh đã được xóa thành công.");
                            } else {
                                Log.d("DeleteUser", "Không thể xóa ảnh.");
                            }
                        } else {
                            // Nếu ảnh không tồn tại, bỏ qua
                            Log.d("DeleteUser", "Ảnh không tồn tại: " + imagePath);
                        }
                    } else {
                        // Đường dẫn ảnh không hợp lệ (null hoặc trống)
                        Log.d("DeleteUser", "Đường dẫn ảnh không hợp lệ.");
                    }

                    // Cập nhật danh sách và RecyclerView
//                        userList.remove(selectedUser);
//                        adapter.updateData(userList);

                    updateUsers();

                    Toast.makeText(AdminDetailManagerUsersActivity.this, "Đã xóa người dùng", Toast.LENGTH_SHORT).show();
                    adapter.clearSelection();
                    dialog.dismiss();
                } else {
                    Toast.makeText(AdminDetailManagerUsersActivity.this, "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
                    adapter.clearSelection();
                }
            }
        });

        dialog.show();
    }

    public void updateUsers() {
        // Lấy danh sách người dùng mới sau khi thao tác xong
        userList = adminRepository.getAllUsersWithRoles();

        // Cập nhật RecyclerView Adapter với dữ liệu mới
        if (userList != null) {
            adapter.updateData(userList); // Cập nhật dữ liệu cho Adapter
            adapter.notifyDataSetChanged(); // Thông báo cho Adapter để cập nhật UI
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.clearSelection();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật dữ liệu khi quay lại Activity
        updateUsers();
    }
}