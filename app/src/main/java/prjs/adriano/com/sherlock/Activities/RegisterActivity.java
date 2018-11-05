package prjs.adriano.com.sherlock.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UsersHelper;

public class RegisterActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        final EditText etUsername = findViewById(R.id.etRegisterUsername);
        final EditText etEmail = findViewById(R.id.etRegisterEmail);
        final EditText etPassword = findViewById(R.id.etRegisterPassword);
        final EditText etConfirmPassowrd = findViewById(R.id.etRegisterConfirmPassword);
        
        
        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String syncUsername = etUsername.getText().toString();
                String syncEmail = etEmail.getText().toString();
                String syncPassword = etPassword.getText().toString();
                String syncConfirmPassword = etConfirmPassowrd.getText().toString();


                
                if(TextUtils.isEmpty(syncUsername)){
                    showSnack("Username required!");
                }else if(TextUtils.isEmpty(syncEmail)){
                    showSnack("Email required!");
                }else if(!Objects.equals(syncPassword, syncConfirmPassword)){
                    showSnack("Passwords don't match!");
                }else{
                    DBHTrip dbhTrip = new DBHTrip(RegisterActivity.this);
                    dbhTrip.clearTrips();
                    Global.setAutologinStatus(RegisterActivity.this, true);
                    UsersHelper.addUser(RegisterActivity.this, syncUsername, syncEmail, syncPassword);
                }
            }
        });
    }

    private void showSnack(String message) {
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}
