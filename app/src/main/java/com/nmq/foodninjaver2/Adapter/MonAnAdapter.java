package com.nmq.foodninjaver2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.Model.MonAn;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Restaurant.PopularMenuActivity;

import java.util.ArrayList;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder> {

    private final Context context;
    private ArrayList<MonAn> listMonAn;

    public MonAnAdapter(Context context, ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lv_monan, parent, false);
        return new MonAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position) {
        MonAn monAn = listMonAn.get(position);
        holder.imageView.setImageResource(monAn.getAnhMonAn());

        // Xử lý sự kiện click
        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PopularMenuActivity.class);
            intent.putExtra("monan_id", monAn.getAnhMonAn()); // Truyền dữ liệu
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMonAn.size();
    }

    // Phương thức hỗ trợ cập nhật danh sách (thay thế cho Filter)
    public void updateList(ArrayList<MonAn> filteredList) {
        this.listMonAn = filteredList;
        notifyDataSetChanged();
    }

    static class MonAnViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.crab4); // ID từ layout lv_monan.xml
        }
    }
}


