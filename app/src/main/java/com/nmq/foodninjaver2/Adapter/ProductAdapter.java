package com.nmq.foodninjaver2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.Model.Product;
import com.nmq.foodninjaver2.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Đảm bảo rằng tên layout đúng với file XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodcut, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ivProductImage);
            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);  // Sửa ID thành tvProductPrice
        }

        public void bind(Product product) {
            // Thiết lập dữ liệu cho các thành phần của item
            productName.setText(product.getName());
            productPrice.setText("$" + product.getPrice());
            // Thiết lập hình ảnh (nên sử dụng thư viện Glide hoặc Picasso nếu có URL hình ảnh)
            // Ví dụ: Glide.with(itemView.getContext()).load(product.getImageUrl()).into(productImage);
        }
    }
}
