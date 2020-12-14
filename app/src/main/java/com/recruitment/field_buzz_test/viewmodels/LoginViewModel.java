package com.recruitment.field_buzz_test.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.recruitment.field_buzz_test.apiutils.AllApiService;
import com.recruitment.field_buzz_test.models.login.LoginResponse;
import com.recruitment.field_buzz_test.models.login.LoginUser;
import com.recruitment.field_buzz_test.callbacks.LoginCallBack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.recruitment.field_buzz_test.apiutils.ApiClient.getClient;
import static com.recruitment.field_buzz_test.utils.Constants.BASE_URL;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(LoginUser loginUser, LoginCallBack loginCallBack){
        loginUser(loginUser, loginCallBack);
    }

    private void loginUser(LoginUser loginUser, LoginCallBack loginCallBack){
        AllApiService apiService = getClient(BASE_URL).create(AllApiService.class);
        final Call<LoginResponse> call = apiService.login(loginUser);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()){
                        LoginResponse loginResponse = response.body();
                        loginCallBack.onLoginSuccess(loginResponse);
                    }
                }
                else{
                    loginCallBack.onUnMatchedEntry("Username or password is not valid");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginCallBack.onLoginFailure("Login failed");
            }
        });
    }
}
