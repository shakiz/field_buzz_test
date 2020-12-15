package com.recruitment.field_buzz_test.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.recruitment.field_buzz_test.R;
import com.recruitment.field_buzz_test.callbacks.RecruitmentCallBack;
import com.recruitment.field_buzz_test.databinding.ActivityMainBinding;
import com.recruitment.field_buzz_test.models.recruitment.CvFile;
import com.recruitment.field_buzz_test.models.recruitment.UserRecruitment;
import com.recruitment.field_buzz_test.utils.PrefManager;
import com.recruitment.field_buzz_test.utils.Tools;
import com.recruitment.field_buzz_test.utils.Validation;
import com.recruitment.field_buzz_test.viewmodels.RecruitmentViewModel;
import com.recruitment.field_buzz_test.viewmodels.SendFileViewModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.recruitment.field_buzz_test.utils.Constants.ACTIVITY_CHOOSE_FILE;
import static com.recruitment.field_buzz_test.utils.Constants.mToken;

public class MainActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private ActivityMainBinding activityMainBinding;
    private Map<String, String[]> validationMap = new HashMap<String, String[]>();
    private Validation validation;
    private String[] applying_in_array = new String[]{"Android","Backend"};
    private String applying_in_str = "";
    private RecruitmentViewModel recruitmentViewModel;
    private SendFileViewModel sendFileViewModel;
    private ProgressDialog dialog;
    private int mFileTokenId;
    private String cvPath = "";
    private File cvPdfFile;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        //region init components and their interactions
        initUI();
        bindUIWithComponents();
        //endregion
    }

    //region init XML components with backend
    private void initUI() {
        prefManager = new PrefManager(this);
        validation = new Validation(this, validationMap);
        dialog = new ProgressDialog(MainActivity.this);
        tools = new Tools(this);
        recruitmentViewModel = ViewModelProviders.of(this).get(RecruitmentViewModel.class);
        sendFileViewModel = ViewModelProviders.of(this).get(SendFileViewModel.class);
    }
    //endregion

    //region perform UI interactions
    private void bindUIWithComponents() {
        //region ask for permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please allow storage permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},007);
        }
        //endregion

        //region applying in spinner adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, applying_in_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainBinding.applyingIn.setAdapter(dataAdapter);
        //endregion

        //region validation
        configureMasterValidation();
        //endregion

        //region attach CV file
        activityMainBinding.cvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowse();
            }
        });
        //endregion

        //region get applying data from spinner listener
        activityMainBinding.applyingIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applying_in_str = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        //region add new record click listener
        activityMainBinding.addNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation.isValid()){
                    dialog.setMessage("Sending data to server....");
                    dialog.show();
                    UserRecruitment userRecruitment = new UserRecruitment();
                    userRecruitment.setTsync_id(UUID.randomUUID().toString());
                    userRecruitment.setName(activityMainBinding.name.getText().toString());
                    userRecruitment.setEmail(activityMainBinding.email.getText().toString());
                    userRecruitment.setPhone(activityMainBinding.phone.getText().toString());
                    userRecruitment.setFull_address(activityMainBinding.fullAddress.getText().toString());
                    userRecruitment.setName_of_university(activityMainBinding.nameOfUniversity.getText().toString());
                    userRecruitment.setGraduation_year(Integer.parseInt(activityMainBinding.graduationYear.getText().toString()));
                    userRecruitment.setCgpa(Double.parseDouble(activityMainBinding.cgpa.getText().toString()));
                    userRecruitment.setExperience_in_months(Integer.parseInt(activityMainBinding.experienceInMonths.getText().toString()));
                    userRecruitment.setCurrent_work_place_name(activityMainBinding.currentWorkPlaceName.getText().toString());
                    userRecruitment.setApplying_in(applying_in_str);
                    userRecruitment.setExpected_salary(Integer.parseInt(activityMainBinding.expectedSalary.getText().toString()));
                    userRecruitment.setField_buzz_reference(activityMainBinding.fieldBuzzReference.getText().toString());
                    userRecruitment.setGithub_project_url(activityMainBinding.githubProjectUrl.getText().toString());
                    userRecruitment.setCv_file(new CvFile(0,UUID.randomUUID().toString()));
                    userRecruitment.setOn_spot_creation_time(System.currentTimeMillis());
                    userRecruitment.setOn_spot_update_time(0);
                    recruitmentViewModel.senData(userRecruitment, prefManager.getString(mToken), new RecruitmentCallBack() {
                        @Override
                        public void OnSuccess(UserRecruitment recruitmentResponse) {
                            Toast.makeText(MainActivity.this, recruitmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w("shakil","File ID : "+recruitmentResponse.getCv_file().getTsync_id());
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            //region send file to server after getting file id
                            if (recruitmentResponse.getCv_file() != null){
                                dialog.setMessage("Sending file to server....");
                                dialog.show();
                                mFileTokenId = recruitmentResponse.getCv_file().getId();
                                //region create request body for file attachment
                                RequestBody reqFile = RequestBody.create(MediaType.parse("application/pdf"), cvPdfFile);
                                MultipartBody.Part file = MultipartBody.Part.createFormData("pdf", cvPdfFile.getName(), reqFile);
                                //endregion
                                //region call for next api to send file object TODO
                                sendFileViewModel.sendFile(mFileTokenId, file, prefManager.getString(mToken), new SendFileViewModel.onFileSend() {
                                    @Override
                                    public void successFul(CvFile cvFile) {
                                        Toast.makeText(MainActivity.this, cvFile.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void unsuccessful(String message) {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                                //endregion
                            }
                            //endregion
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        //endregion
    }
    //endregion

    //region validation part
    private void configureMasterValidation() {
        validation.setEditTextIsNotEmpty(
                new String[]{"name", "email","phone","full_address","name_of_university","graduation_year","cgpa","experience_in_months","current_work_place_name",
                        "expected_salary","github_project_url","cv_file"},
                new String[]{"Please enter name","Please enter email","Please enter mobile no","Please provide full address","Provide your university name",
                        "Please give your graduation year","Please enter your cgpa","Please enter experience you have","Please provide your current organisation",
                        "Please give your expected salary","Provide your project git url","Please attach your CV"}
        );
    }
    //endregion

    //region open file chooser
    public void onBrowse() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, ACTIVITY_CHOOSE_FILE);
    }
    //endregion

    //region activity components
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if(requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uploadfileuri = data.getData();
            cvPath = tools.getPath(uploadfileuri);
            if (cvPath != null){
                cvPdfFile = new File(cvPath);
                activityMainBinding.cvFile.setText(cvPdfFile.getName());
            }
            else{
                Toast.makeText(this, "CV not attached", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        tools.exitApp();
    }
    //endregion
}