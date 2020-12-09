package com.recruitment.field_buzz_test.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.recruitment.field_buzz_test.R;
import com.recruitment.field_buzz_test.utils.PrefManager;

public class MainActivity extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region init components and their interactions
        initUI();
        bindUIWithComponents();
        //endregion
    }

    //region init XML components with backend
    private void initUI() {
        prefManager = new PrefManager(this);
    }
    //endregion

    //region perform UI interactions
    private void bindUIWithComponents() {

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