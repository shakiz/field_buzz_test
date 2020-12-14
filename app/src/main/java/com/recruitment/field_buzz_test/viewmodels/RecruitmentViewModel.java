package com.recruitment.field_buzz_test.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;
import com.recruitment.field_buzz_test.apiutils.AllApiService;
import com.recruitment.field_buzz_test.models.recruitment.UserRecruitment;
import com.recruitment.field_buzz_test.callbacks.RecruitmentCallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.recruitment.field_buzz_test.apiutils.ApiClient.getClient;
import static com.recruitment.field_buzz_test.utils.Constants.BASE_URL;

public class RecruitmentViewModel extends AndroidViewModel {
    public RecruitmentViewModel(@NonNull Application application) {
        super(application);
    }
    public void senData(UserRecruitment userRecruitment, String token, RecruitmentCallBack recruitmentCallBack){
        loginUser(userRecruitment, token, recruitmentCallBack);
    }

    private void loginUser(UserRecruitment userRecruitment, String token, RecruitmentCallBack recruitmentCallBack){
        AllApiService apiService = getClient(BASE_URL).create(AllApiService.class);
        final Call<UserRecruitment> call = apiService.sendDataToServer(userRecruitment,"Token "+token);
        printJson("Recruitment Response", userRecruitment);
        call.enqueue(new Callback<UserRecruitment>() {
            @Override
            public void onResponse(Call<UserRecruitment> call, Response<UserRecruitment> response) {
                printJson("Login Response", response.body());
                if (response.isSuccessful()){
                    if (response.body().getSuccess()){
                        recruitmentCallBack.OnSuccess(response.body());
                    }
                }
                else{
                    recruitmentCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<UserRecruitment> call, Throwable t) {
                recruitmentCallBack.onFailure("Data sending failed..");
            }
        });
    }

    public void printJson(String tableName, Object object) {
        Log.w("shakil", tableName + " JSON: " + new Gson().toJson(object));
    }
}
