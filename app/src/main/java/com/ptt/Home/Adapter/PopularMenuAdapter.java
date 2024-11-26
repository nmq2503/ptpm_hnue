package com.ptt.Home.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.ptt.Home.Domain.MenuDomain;

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

        int drawableResourceId = holder.itemView.getResources().getIdentifier(
                currentItem.getPic(), "drawable", holder.itemView.getContext().getPackageName()
        );

        holder.menuPic.setImageResource(drawableResourceId);
    }

    @Override
    public int getItemCount() {
        return menuDomain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        ImageView menuPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuPic = itemView.findViewById(R.id.menuPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
