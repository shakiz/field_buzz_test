package com.recruitment.field_buzz_test.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.recruitment.field_buzz_test.R;
import com.recruitment.field_buzz_test.models.login.LoginResponse;
import com.recruitment.field_buzz_test.models.login.LoginUser;
import com.recruitment.field_buzz_test.utils.LoginCallBack;
import com.recruitment.field_buzz_test.utils.PrefManager;
import com.recruitment.field_buzz_test.viewmodels.LoginViewModel;
import static com.recruitment.field_buzz_test.utils.Constants.mIsLoggedIn;
import static com.recruitment.field_buzz_test.utils.Constants.mToken;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText username,password;
    private PrefManager prefManager;
    private LoginViewModel loginViewModel;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region init components and their interactions
        initUI();
        bindUIWithComponents();
        //endregion
    }

    //region init XML components with backend
    private void initUI() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        prefManager = new PrefManager(this);
        dialog = new ProgressDialog(LoginActivity.this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }
    //endregion

    //region perform UI interactions
    private void bindUIWithComponents() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Signing in....");
                dialog.show();
                LoginUser loginUser = new LoginUser(username.getText().toString(),password.getText().toString());
                loginViewModel.login(loginUser, new LoginCallBack() {
                    @Override
                    public void onLoginSuccess(LoginResponse loginResponse) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (loginResponse.getSuccess()){
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            prefManager.set(mIsLoggedIn,true);
                            prefManager.set(mToken,loginResponse.getToken());
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }

                    @Override
                    public void onUnMatchedEntry(String message) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoginFailure(String message) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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