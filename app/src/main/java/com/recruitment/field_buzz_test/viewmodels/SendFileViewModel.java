package com.recruitment.field_buzz_test.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.gson.Gson;
import com.recruitment.field_buzz_test.apiutils.AllApiService;
import com.recruitment.field_buzz_test.models.recruitment.CvFile;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.recruitment.field_buzz_test.apiutils.ApiClient.getClient;
import static com.recruitment.field_buzz_test.utils.Constants.BASE_URL;

public class SendFileViewModel extends AndroidViewModel {
    public SendFileViewModel(@NonNull Application application) {
        super(application);
    }

    public void sendFile(int fileTokenId, MultipartBody.Part pdfFile, String token, onFileSend onFileSend){
        loginUser(fileTokenId, pdfFile, token, onFileSend);
    }

    private void loginUser(int fileTokenId, MultipartBody.Part pdfFile, String token, onFileSend onFileSend){
        AllApiService apiService = getClient(BASE_URL).create(AllApiService.class);
        final Call<CvFile> call = apiService.sendFileToServer(fileTokenId,"Token "+token, pdfFile);
        call.enqueue(new Callback<CvFile>() {
            @Override
            public void onResponse(Call<CvFile> call, Response<CvFile> response) {
                printJson("Send File Response", response.body());
                if (response.isSuccessful()){
                    onFileSend.successFul(response.body());
                }
                else{
                    onFileSend.unsuccessful("Some internal issue");
                }
            }

            @Override
            public void onFailure(Call<CvFile> call, Throwable t) {
                onFileSend.unsuccessful("File sending failed..\n"+t.getMessage());
            }
        });
    }

    public void printJson(String tableName, Object object) {
        Log.w("shakil", tableName + " JSON: " + new Gson().toJson(object));
    }

    public interface onFileSend{
        void successFul(CvFile cvFile);
        void unsuccessful(String message);
    }
}
