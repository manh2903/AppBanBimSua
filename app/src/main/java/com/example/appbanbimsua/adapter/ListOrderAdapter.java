package com.example.appbanbimsua.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanbimsua.R;
import com.example.appbanbimsua.enitities.response.OrderList;

import java.util.List;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.OrderListViewHolder> {

    private List<OrderList> orders;

    public ListOrderAdapter(List<OrderList> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        OrderList order = orders.get(position);
        holder.billCodeTextView.setText("Mã đơn hàng: "+order.getBillCode());
        holder.quantityTextView.setText("Số lượng: "+String.valueOf(order.getQuantity()));
        holder.totalPriceTextView.setText(String.format("Tổng tiền: %,d đ", order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderListViewHolder extends RecyclerView.ViewHolder {
        TextView billCodeTextView;
        TextView quantityTextView;
        TextView totalPriceTextView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            billCodeTextView = itemView.findViewById(R.id.tv_bill_name);
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            totalPriceTextView = itemView.findViewById(R.id.tv_tong_tien);
        }
    }
}
