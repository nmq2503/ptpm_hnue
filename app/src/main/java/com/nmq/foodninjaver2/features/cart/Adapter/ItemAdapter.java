package com.nmq.foodninjaver2.features.cart.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.cart.Adapter.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;
    private boolean bIsMenuList;

    public ItemAdapter(Context context, int resource, List<Item> objects, boolean bIsMenuList) {
        super(context, resource, objects);
        this.resource = resource;
        this.bIsMenuList = bIsMenuList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Item item = getItem(position);
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemSeqNum = view.findViewById(R.id.item_seq_number);
            viewHolder.itemName = view.findViewById(R.id.item_name);
            viewHolder.itemPrice = view.findViewById(R.id.item_price);
            viewHolder.itemOrdNum = view.findViewById(R.id.item_ord_number);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (item != null) {
            viewHolder.itemSeqNum.setText(String.valueOf(item.iItemSeqNum));
            viewHolder.itemName.setText(item.strItemName);
            viewHolder.itemPrice.setText(String.format("%.2f", item.dItemPrice));
            viewHolder.itemOrdNum.setText(String.valueOf(item.iItemOrdNum));

            if (bIsMenuList) {
                if (item.iItemOrdNum != 0) {
                    viewHolder.itemOrdNum.setBackgroundColor(Color.parseColor("#FF99FF"));
                } else {
                    viewHolder.itemOrdNum.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        }

        return view;
    }

    static class ViewHolder {
        TextView itemSeqNum;
        TextView itemName;
        TextView itemPrice;
        TextView itemOrdNum;
    }
}
