package com.recruitment.field_buzz_test.apiutils;

import com.recruitment.field_buzz_test.models.login.LoginResponse;
import com.recruitment.field_buzz_test.models.login.LoginUser;
import com.recruitment.field_buzz_test.models.recruitment.UserRecruitment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AllApiService {
    //Call for login
    @POST("login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    //Call for posting response
    @POST("v0/recruiting-entities/")
    Call<UserRecruitment> sendDataToServer(@Body UserRecruitment userRecruitment,@Header("Authorization") String token);
}
