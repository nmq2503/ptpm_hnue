package com.nmq.foodninjaver2.features.Home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.MenuDetail.ItemMenuActivity;
import com.nmq.foodninjaver2.features.Home.MenuDetail.MenuDetailActivity;
import com.nmq.foodninjaver2.features.Home.Model.MenuDomain;

import java.util.ArrayList;

public class PopularMenuAdapter extends RecyclerView.Adapter<PopularMenuAdapter.ViewHolder> {
    ArrayList<MenuDomain> menuDomain;
    public PopularMenuAdapter(ArrayList<MenuDomain> menuDomain) {
        this.menuDomain = menuDomain;
    }

    public void updateList(ArrayList<MenuDomain> newList) {
        menuDomain.clear();
        menuDomain.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuName, fee;
        ImageView menuPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.tvMenuName);
            menuPic = itemView.findViewById(R.id.tvPic);
            fee = itemView.findViewById(R.id.fee);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular_menu, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuDomain currentItem = menuDomain.get(position);
        holder.menuName.setText(currentItem.getTitle());
        holder.fee.setText(String.valueOf(currentItem.getFee()));

        // Kiểm tra đường dẫn ảnh
        String imageUrl = currentItem.getPic();
        Context context = holder.itemView.getContext();
        if (imageUrl == null || imageUrl.isEmpty()) {
            // Load ảnh mặc định nếu đường dẫn ảnh rỗng/null
            holder.menuPic.setImageResource(R.drawable.icon_undefine_user
            );
        } else {
            // Nếu menuPic là tên file trong drawable
            int drawableResourceId = context.getResources().getIdentifier(
                    imageUrl, "drawable", context.getPackageName()
            );
            if (drawableResourceId != 0) {
                // Nếu tìm thấy ảnh trong drawable, load ảnh
                Glide.with(context)
                        .load(drawableResourceId)
                        .error(R.drawable.icon_undefine_user) // Ảnh mặc định nếu có lỗi
                        .into(holder.menuPic);
            } else {
                // Nếu không tìm thấy, hiển thị ảnh mặc định
                holder.menuPic.setImageResource(R.drawable.icon_undefine_user);
            }
        }

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ItemMenuActivity.class);
            intent.putExtra("menuTitle", currentItem.getTitle());
            intent.putExtra("menuPic", currentItem.getPic());
            intent.putExtra("menuFee", currentItem.getFee());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuDomain.size();
    }

}
