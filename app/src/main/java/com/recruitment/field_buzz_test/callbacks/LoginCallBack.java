package com.recruitment.field_buzz_test.callbacks;

import com.recruitment.field_buzz_test.models.login.LoginResponse;

public interface LoginCallBack {
    void onLoginSuccess(LoginResponse loginResponse);

    void onUnMatchedEntry(String message);

    void onLoginFailure(String message);
}
