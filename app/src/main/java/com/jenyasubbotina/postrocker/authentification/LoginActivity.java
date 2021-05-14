package com.jenyasubbotina.postrocker.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.SessionManager;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.TokenAuthBodyPojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenRefreshBodyPojo;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(v -> {
            String name, pass;
            name = username.getText().toString().trim();
            pass = password.getText().toString().trim();
            signIn(name, pass);
        });
    }

    public void signIn(String username, String password) {
        NetworkService.getInstance()
                .getJSONApi()
                .getTokenAuth(new TokenAuthBodyPojo(username, password))
                .enqueue(new Callback<TokenAuthResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TokenAuthResponsePojo> call, @NotNull Response<TokenAuthResponsePojo> response) {
                        if (response.code() == 200 && response.body() != null) {
                            String refresh = response.body().getRefreshToken();
                            String access = response.body().getAccessToken();
                            sm.saveRefreshToken(refresh);
                            sm.saveAuthToken(access);
                            refreshToken(access);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<TokenAuthResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void refreshToken(String old) {
        NetworkService.getInstance()
                .getJSONApi()
                .refreshTokenAuth(new TokenRefreshBodyPojo(old))
                .enqueue(new Callback<TokenAuthResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TokenAuthResponsePojo> call,
                                           @NotNull Response<TokenAuthResponsePojo> response) {
                        if (response.code() == 200 && response.body() != null) {
                            sm.saveAuthToken(response.body().getAccessToken());
                            sm.saveRefreshToken(response.body().getRefreshToken());
                        } else if (response.code() == 401) {
                            sm.refreshToken(sm.fetchRefreshToken());
                        } else if (response.code() == 400) {
                            sm.saveRefreshToken(null);
                            sm.saveAuthToken(null);
                            Toast.makeText(LoginActivity.this,
                                    LoginActivity.this.getString(R.string.login_first), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<TokenAuthResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void init() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sm = new SessionManager(LoginActivity.this);
    }
}
