package com.example.appbanbimsua.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanbimsua.R;
import com.example.appbanbimsua.adapter.CartAdapter;
import com.example.appbanbimsua.api.ApiService;
import com.example.appbanbimsua.api.RetrofitClient;
import com.example.appbanbimsua.enitities.ProductCart;
import com.example.appbanbimsua.enitities.response.CartResponse;
import com.example.appbanbimsua.enitities.response.UserResponse;
import com.example.appbanbimsua.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rcv_cart;
    private CartAdapter cartAdapter;
    private TextView txt_tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rcv_cart = findViewById(R.id.rcv_cart);
        txt_tongtien = findViewById(R.id.txt_tongtien);
        rcv_cart.setLayoutManager(new LinearLayoutManager(this));

        if (Utils.isLoggedIn(this)) {
            UserResponse userResponse = Utils.getUserInfo(this);
            getCartByUserId((long) userResponse.getId());
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(cartUpdateReceiver,
                new IntentFilter("UPDATE_CART"));
    }
    private final BroadcastReceiver cartUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Reload dữ liệu giỏ hàng
            if (Utils.isLoggedIn(CartActivity.this)) {
                UserResponse userResponse = Utils.getUserInfo(CartActivity.this);
                getCartByUserId((long) userResponse.getId());
            }
        }
    };
    private void getCartByUserId(Long userId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<CartResponse> call = apiService.getCartByUserId(userId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse != null) {
                        List<ProductCart> productList = cartResponse.getProducts();
                        cartAdapter = new CartAdapter(CartActivity.this, productList);
                        rcv_cart.setAdapter(cartAdapter);

                        long totalAmount = 0;
                        for (ProductCart product : productList) {
                            totalAmount += product.getTotalMoney();
                        }
                        txt_tongtien.setText(String.format("Tổng tiền: %,d đ", totalAmount));
                    }
                } else {
                    Log.e("API_RESPONSE", "Response không thành công");
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cartUpdateReceiver);
    }
}
