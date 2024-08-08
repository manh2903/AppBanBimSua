package com.example.appbanbimsua.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.appbanbimsua.R;
import com.example.appbanbimsua.respone.Product;
import com.example.appbanbimsua.utils.Utils;

import java.util.List;

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
                    .into(holder.imghinhanh);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class SanPhamHome extends RecyclerView.ViewHolder {
        TextView txtgia,txtten;
        ImageView imghinhanh;



        public SanPhamHome(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.tv_ten_sp);
            txtten = itemView.findViewById(R.id.tv_gia_sp);
            imghinhanh = itemView.findViewById(R.id.itemsp_image);
        }
    }
}
