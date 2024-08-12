package com.example.appbanbimsua.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanbimsua.R;
import com.example.appbanbimsua.adapter.ListOrderAdapter;
import com.example.appbanbimsua.api.ApiService;
import com.example.appbanbimsua.api.RetrofitClient;
import com.example.appbanbimsua.enitities.response.OrderList;
import com.example.appbanbimsua.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingPickupFragment extends Fragment {
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ListOrderAdapter orderAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_waiting_pickup, container, false);
        initUI();
        createOrder();
        return view;
    }

    private void initUI() {
        recyclerView = view.findViewById(R.id.recyclerViewWaitingPickup); // Thay đổi ở đây
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());
    }

    private void createOrder() {
        showProgress();
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<OrderList>> call = apiService.getOrders((long) Utils.getUserInfo(getContext()).getId(), 1);
        call.enqueue(new Callback<List<OrderList>>() {
            @Override
            public void onResponse(Call<List<OrderList>> call, Response<List<OrderList>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderList> orders = response.body();
                    orderAdapter = new ListOrderAdapter(orders);
                    recyclerView.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OrderList>> call, Throwable t) {
                progressDialog.dismiss();
                // Xử lý lỗi
            }
        });
    }

    private void showProgress() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
