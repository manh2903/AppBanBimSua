package com.example.appbanbimsua.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanbimsua.R;
import com.example.appbanbimsua.activity.ProductDetailActivity;
import com.example.appbanbimsua.api.ApiService;
import com.example.appbanbimsua.api.RetrofitClient;
import com.example.appbanbimsua.enitities.ProductCart;
import com.example.appbanbimsua.enitities.response.CartResponse;
import com.example.appbanbimsua.enitities.response.ResponseOK;
import com.example.appbanbimsua.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ProductCart> productList;
    public String total;

    public CartAdapter(Context context, List<ProductCart> productList) {
        this.context = context;
        this.productList = productList;
    }
    public List<ProductCart> getProductList() {
        return productList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductCart product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.format("%,d đ", product.getPrice()));
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
        String img = Utils.BASE_URL + product.getImages().get(0);

//        Glide.with(context)
//            .load(product.getImages().get(0))
//            .into(holder.imgProduct);
        Glide.with(holder.itemView.getContext())
                .load(img)
                .error(R.drawable.ic_home)
                .override(holder.imgProduct.getWidth(), holder.imgProduct.getHeight())
                .centerCrop() // scale to fit entire view
                .into(holder.imgProduct);

        // Sự kiện tăng số lượng
        holder.btnIncreaseQuantity.setOnClickListener(v -> {
            addOrUpdateCartItem((long) Utils.getUserInfo(context).getId(),product.getId());
            Intent intent = new Intent("UPDATE_CART");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });

        // Sự kiện giảm số lượng
        holder.btnDecreaseQuantity.setOnClickListener(v -> {
            decreaseCartItem((long) Utils.getUserInfo(context).getId(),product.getId());
            Intent intent = new Intent("UPDATE_CART");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productSlug = product.getSlug();
                String productId = product.getId();


                //    getProductDetail(productSlug, productId);

                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productSlug", productSlug);
                intent.putExtra("productId", productId);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvQuantity;
        Button btnIncreaseQuantity, btnDecreaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnIncreaseQuantity = itemView.findViewById(R.id.btn_increase_quantity);
            btnDecreaseQuantity = itemView.findViewById(R.id.btn_decrease_quantity);
        }
    }
    private void addOrUpdateCartItem(Long userId, String productId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseOK> call = apiService.addOrUpdateCartItem(userId, productId);
        call.enqueue(new Callback<ResponseOK>() {
            @Override
            public void onResponse(Call<ResponseOK> call, Response<ResponseOK> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseOK> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void decreaseCartItem(Long userId, String productId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseOK> call = apiService.decreaseCartItem(userId, productId);
        call.enqueue(new Callback<ResponseOK>() {
            @Override
            public void onResponse(Call<ResponseOK> call, Response<ResponseOK> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseOK> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    public void removeItem(int position) {
        if (position >= 0 && position < productList.size()) {
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        }
    }
}
