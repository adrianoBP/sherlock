package prjs.adriano.com.sherlock.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import prjs.adriano.com.sherlock.Requests.UsersHelper;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.splashes.AfterLogSplash;

/**
 * Created by Adriano on 04/02/2018.
 */

public class LoginActivity extends AppCompatActivity {

    //VALUES
    //COMPONENTS
    Button loginButton;
    EditText etUsername;
    EditText etPassword;
    TextView tvRegister;

    @Override
    public void onBackPressed() {
        LoginActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        //region COMPONENTS INITIALIZATION
        loginButton = findViewById(R.id.btnlogin);
        etUsername = findViewById(R.id.txtUsername);
        etPassword = findViewById(R.id.txtLoginPassword);
        tvRegister = findViewById(R.id.tvRegister);

        //enregion
        //region LISTENERS
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                UsersHelper.getUsers(LoginActivity.this, username, password);

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });


        //endregion
    }


    public boolean tryLogin() {
        if (Global.returnAutologinStatus(this))
            return true;
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        UsersHelper.getUsers(this, username, password);
        return true;
    }


    private void createMainActivity() {
        Intent intent = new Intent(LoginActivity.this, AfterLogSplash.class);
        LoginActivity.this.finish();
        startActivity(intent);
        ((Activity) LoginActivity.this).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }


}
