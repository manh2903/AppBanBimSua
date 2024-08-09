package com.example.appbanbimsua.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.appbanbimsua.R;
import com.example.appbanbimsua.activity.ProductDetailActivity;
import com.example.appbanbimsua.api.ApiService;
import com.example.appbanbimsua.api.RetrofitClient;
import com.example.appbanbimsua.enitities.Product;
import com.example.appbanbimsua.enitities.respone.ProductDetailResponse;
import com.example.appbanbimsua.enitities.respone.ProductResponse;
import com.example.appbanbimsua.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamHomeAdapter extends RecyclerView.Adapter<SanPhamHomeAdapter.SanPhamHome> {

    Context context;
    List<Product> array;
    public SanPhamHomeAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public SanPhamHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_home,parent,false);
        return new SanPhamHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHome holder, int position) {
        Product product = array.get(position);
        if(array == null){
            return;
        }
        else {
            holder.txtten.setText(product.getName());
            holder.txtgia.setText(String.valueOf(product.getPrice()));
            String img = Utils.BASE_URL + product.getImages();
            Glide.with(holder.itemView.getContext())
                    .load(img)
                    .error(R.drawable.ic_home)
                    .override(holder.imghinhanh.getWidth(), holder.imghinhanh.getHeight())
                    .centerCrop() // scale to fit entire view
                    .into(holder.imghinhanh);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getProductDetail(product.getSlug(), product.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(array == null){
            return 0;
        }
        return array.size();
    }

    public class SanPhamHome extends RecyclerView.ViewHolder {
        TextView txtgia,txtten;
        ImageView imghinhanh;

        public SanPhamHome(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.tv_gia_sp);
            txtten = itemView.findViewById(R.id.tv_ten_sp);
            imghinhanh = itemView.findViewById(R.id.itemsp_image);
        }
    }

    public void getProductDetail(String tenSp, String id){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ProductDetailResponse> call = apiService.getProductDetails(tenSp, id);
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailResponse ProductDetailResponse = response.body();
                    // Chuyển sang màn chi tiết sản phẩm nếu gọi API thành công
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("productResponse", ProductDetailResponse);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
