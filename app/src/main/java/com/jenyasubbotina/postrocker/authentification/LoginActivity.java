package com.jenyasubbotina.postrocker.authentification;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.SessionManager;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.AllUsersResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthBodyPojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenRefreshBodyPojo;
import com.jenyasubbotina.postrocker.pojo.UserPojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login, back;
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
                            getId(username);
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
                            back.performClick();
                        } else if (response.code() == 401) {
                            refreshToken(sm.fetchRefreshToken());
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

    public void getId(String name) {
        NetworkService.getInstance()
                .getJSONApi()
                .getUserByUsername(name)
                .enqueue(new Callback<AllUsersResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<AllUsersResponsePojo> call,
                                           @NotNull Response<AllUsersResponsePojo> response) {
                        if (response.code() == 200 && response.body() != null) {
                            ArrayList<UserPojo> match = response.body().getUsers();
                            for (UserPojo u : match) {
                                if (u.getUsername().equals(name)) {
                                    sm.saveUserId(u.getId());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AllUsersResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void init() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sm = new SessionManager(LoginActivity.this);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            finish();
        });
    }
}
