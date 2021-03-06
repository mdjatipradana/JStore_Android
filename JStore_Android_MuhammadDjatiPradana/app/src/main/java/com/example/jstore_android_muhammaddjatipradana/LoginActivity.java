package com.example.jstore_android_muhammaddjatipradana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) findViewById(R.id.registerClickButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse != null){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                builder1.setMessage("Login Success!").create().show();
                                Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainActivityIntent.putExtra("id_customer", jsonResponse.getInt("id"));
                                mainActivityIntent.putExtra("name_customer", jsonResponse.getString("name"));
                                startActivity(mainActivityIntent);
                                finish();
                            }
                        }
                        catch (JSONException e){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setMessage("Login Failed!").create().show();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrationIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registrationIntent);
            }
        });
    }
}