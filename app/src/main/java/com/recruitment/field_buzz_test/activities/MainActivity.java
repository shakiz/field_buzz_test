package com.recruitment.field_buzz_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.recruitment.field_buzz_test.R;
import com.recruitment.field_buzz_test.databinding.ActivityMainBinding;
import com.recruitment.field_buzz_test.utils.PrefManager;
import com.recruitment.field_buzz_test.utils.Validation;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private ActivityMainBinding activityMainBinding;
    private Map<String, String[]> validationMap = new HashMap<String, String[]>();
    private Validation validation;

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
    }
    //endregion

    //region perform UI interactions
    private void bindUIWithComponents() {
        configureMasterValidation();
        //region add new record click listener
        activityMainBinding.addNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation.isValid()){

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
                        "expected_salary","github_project_url"},
                new String[]{"Please enter name","Please enter email","Please enter mobile no","Please provide full address","Provide your university name",
                        "Please give your graduation year","Please enter your cgpa","Please enter experience you have","Please provide your current organisation",
                        "Please give your expected salary","Provide your project git url"}
        );
        validation.setSpinnerIsNotEmpty(new String[]{"applying_in"});
    }
    //endregion

    //region activity components
    @Override
    public void onBackPressed() {
        Intent exitIntent = new Intent(Intent.ACTION_MAIN);
        exitIntent.addCategory(Intent.CATEGORY_HOME);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exitIntent);
    }
    //endregion
}