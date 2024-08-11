package com.example.appbanbimsua.api;// ApiService.java
import com.example.appbanbimsua.enitities.request.CommentRequest;
import com.example.appbanbimsua.enitities.request.LoginRequest;
import com.example.appbanbimsua.enitities.request.SignUpRequest;
import com.example.appbanbimsua.enitities.response.CartResponse;
import com.example.appbanbimsua.enitities.response.ProductDetailResponse;
import com.example.appbanbimsua.enitities.response.ProductResponse;
import com.example.appbanbimsua.enitities.response.ResponseOK;
import com.example.appbanbimsua.enitities.response.SignUpResponse;
import com.example.appbanbimsua.enitities.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);
    @POST("/api/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
    @GET("/products")
    Call<ProductResponse> getProducts();
    @GET("/chitiet/{slug}/{id}")
    Call<ProductDetailResponse> getProductDetails(
            @Path("slug") String slug,
            @Path("id") String id
    );
    @POST("/api/comments/product")
    Call<ResponseOK> postComment(@Body CommentRequest commentRequest);
    @POST("/api/cart/addOrUpdateCart")
    Call<ResponseOK> addOrUpdateCartItem(
            @Query("userId") Long userId,
            @Query("productId") String productId
    );
    @PUT("/api/cart/decrease")
    Call<ResponseOK> decreaseCartItem(
            @Query("userId") Long userId,
            @Query("productId") String productId
    );
    @GET("/api/cart/getCartByUserId")
    Call<CartResponse> getCartByUserId(
            @Query("userId") Long userId
    );
}
