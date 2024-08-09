package com.example.appbanbimsua.api;// ApiService.java
import com.example.appbanbimsua.enitities.request.LoginRequest;
import com.example.appbanbimsua.enitities.request.SignUpRequest;
import com.example.appbanbimsua.enitities.respone.ProductDetailResponse;
import com.example.appbanbimsua.enitities.respone.ProductResponse;
import com.example.appbanbimsua.enitities.respone.SignUpResponse;
import com.example.appbanbimsua.enitities.respone.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);
    @POST("/api/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
    @GET("products")
    Call<ProductResponse> getProducts();
    @GET("/chitiet/{slug}/{id}")
    Call<ProductDetailResponse> getProductDetails(
            @Path("slug") String slug,
            @Path("id") String id
    );


}
