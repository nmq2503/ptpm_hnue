package com.nmq.foodninjaver2.features.cart.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nmq.foodninjaver2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MenuCartFragment extends Fragment {
    private SharedPreferences sp;
    private double totalValue;
    private View.OnClickListener clickHandler;

    private ItemAdapter mCartAdapter;
    static final List<Item> itemOrderedList = new ArrayList<>();
    private TextView cartTotalPrice;
    private ListView cartListView;
    private Button btnCartOrder, btnCartReset;

    @SuppressWarnings("NonConstantResourceId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sp = requireActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);

        // Khởi tạo danh sách đơn hàng
        initOrderedList();
        View v = inflater.inflate(R.layout.menu_cart, container, false);

        // Ánh xạ các thành phần giao diện
        cartTotalPrice = v.findViewById(R.id.cart_total_price);
        cartTotalPrice.setText(new DecimalFormat("##.##").format(totalValue));

        cartListView = v.findViewById(R.id.cart_list);
        mCartAdapter = new ItemAdapter(requireActivity(), R.layout.test, itemOrderedList, false);
        cartListView.setAdapter(mCartAdapter);

        // Khởi tạo sự kiện cho các nút
        initDisplay(v);

        return v;
    }

    private void initOrderedList() {
        itemOrderedList.clear();

        // Lấy dữ liệu từ SharedPreferences
        int[] tmpArray = parseFromSharedPreferences(sp);
        if (tmpArray != null) {
            for (int i = 0; i < tmpArray.length; i++) {
                if (tmpArray[i] != 0) {
                    Item item = new Item(i + 1, "Item #" + (i + 1), 10.0 * (i + 1), tmpArray[i], DetailActivity.imgArr[i]);
                    itemOrderedList.add(item);
                }
            }
        }
        calculateTotalValue();
    }

    private void calculateTotalValue() {
        totalValue = 0;
        for (Item item : itemOrderedList) {
            totalValue += item.iItemOrdNum * item.dItemPrice;
        }
    }

    private void initDisplay(View v) {
        btnCartOrder = v.findViewById(R.id.cart_order);
        btnCartReset = v.findViewById(R.id.cart_reset);

        clickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.cart_reset) {
                    resetOrderListCart();
                } else if (id == R.id.cart_order) {
                    orderThisItemCart();
                }
            }
        };

        btnCartOrder.setOnClickListener(clickHandler);
        btnCartReset.setOnClickListener(clickHandler);
    }

    private void resetOrderListCart() {
        // Xóa danh sách đặt hàng
        MenuListFragment.itemList.clear();
        for (int i = 0; i < DetailActivity.imgArr.length; i++) {
            Item item = new Item(i + 1, "Item #" + (i + 1), 10.0 * (i + 1), 0, DetailActivity.imgArr[i]);
            MenuListFragment.itemList.add(item);
        }

        // Lưu trạng thái mới vào SharedPreferences
        saveToSharedPreferences("itemList", MenuListFragment.itemList);

        // Làm mới giao diện danh sách
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_fragment_list, new MenuListFragment());
        fragmentTransaction.commit();

        itemOrderedList.clear();
        mCartAdapter.notifyDataSetChanged();
        calculateTotalValue();
        cartTotalPrice.setText(new DecimalFormat("##.##").format(totalValue));
    }

    private void saveToSharedPreferences(String key, List<Item> list) {
        StringBuilder sb = new StringBuilder();
        for (Item item : list) {
            sb.append(item.iItemOrdNum).append('-');
        }
        sp.edit().putString(key, sb.toString()).apply();
    }

    private int[] parseFromSharedPreferences(SharedPreferences sp) {
        String strTmp = sp.getString("itemList", null);
        if (strTmp == null || strTmp.isEmpty()) return null;

        String[] tmpStrArray = strTmp.split("-");
        int[] array = new int[tmpStrArray.length];
        try {
            for (int i = 0; i < array.length; i++) {
                array[i] = Integer.parseInt(tmpStrArray[i]);
            }
        } catch (NumberFormatException e) {
            return null; // Xử lý lỗi số không hợp lệ
        }
        return array;
    }

    private void orderThisItemCart() {
        Toast.makeText(requireActivity(),
                "Your order has been placed, total price is Rs." + new DecimalFormat("##.##").format(totalValue),
                Toast.LENGTH_SHORT).show();
        resetOrderListCart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCartAdapter.notifyDataSetChanged();
        calculateTotalValue();
        cartTotalPrice.setText(new DecimalFormat("##.##").format(totalValue));
    }
}
