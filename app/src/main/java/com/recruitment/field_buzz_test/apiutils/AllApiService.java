package com.recruitment.field_buzz_test.apiutils;

import com.recruitment.field_buzz_test.models.login.LoginResponse;
import com.recruitment.field_buzz_test.models.login.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AllApiService {
    //Call for login
    @POST("login")
    Call<LoginResponse> login(@Body LoginUser loginUser);
}
