package com.recruitment.field_buzz_test.apiutils;

import com.recruitment.field_buzz_test.models.login.LoginResponse;
import com.recruitment.field_buzz_test.models.login.LoginUser;
import com.recruitment.field_buzz_test.models.recruitment.CvFile;
import com.recruitment.field_buzz_test.models.recruitment.UserRecruitment;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AllApiService {
    //Call for login
    @POST("login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    //Call for posting response
    @POST("v1/recruiting-entities/")
    Call<UserRecruitment> sendDataToServer(@Body UserRecruitment userRecruitment,@Header("Authorization") String token);

    //Call for posting cv file
    @Multipart
    @PUT("file-object/{id}/")
    Call<CvFile> sendFileToServer(@Path("id") int id, @Header("Authorization") String token, @Part MultipartBody.Part pdfFile);
}
