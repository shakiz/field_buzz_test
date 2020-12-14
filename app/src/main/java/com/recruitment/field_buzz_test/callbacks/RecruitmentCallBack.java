package com.recruitment.field_buzz_test.callbacks;

import com.recruitment.field_buzz_test.models.recruitment.UserRecruitment;

public interface RecruitmentCallBack {
    void OnSuccess(UserRecruitment recruitmentResponse);

    void onFailure(String message);
}
