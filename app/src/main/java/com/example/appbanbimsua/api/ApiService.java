package com.example.appbanbimsua.api;// ApiService.java
import com.example.appbanbimsua.request.LoginRequest;
import com.example.appbanbimsua.request.SignUpRequest;
import com.example.appbanbimsua.respone.SignUpResponse;
import com.example.appbanbimsua.respone.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);
    @POST("/api/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
}
