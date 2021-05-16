package com.jenyasubbotina.postrocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.TokenAuthBodyPojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenRefreshBodyPojo;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionManager {
    Context context;
    public SessionManager(Context context) {
        this.context = context;
    }
    private SharedPreferences prefs;

    public Long getUserId() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getLong(Constants.USER_ID, 0L);
    }

    public void saveUserId(Long id) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Constants.USER_ID, id);
        editor.apply();
    }

    public void resetTokens() {
        saveAuthToken(null);
        saveRefreshToken(null);
    }

    public void saveAuthToken(String token) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.TOKEN, null);
    }

    public void saveRefreshToken(String token) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.REFRESH_TOKEN, token);
        editor.apply();
    }

    public String fetchRefreshToken() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.REFRESH_TOKEN, null);
    }

    public boolean isAuthenticated() {
        return (fetchAuthToken() != null);
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
                            saveAuthToken(response.body().getAccessToken());
                            saveRefreshToken(response.body().getRefreshToken());
                        } else if (response.code() == 401) {
                            refreshToken(fetchRefreshToken());
                        } else if (response.code() == 400) {
                            saveRefreshToken(null);
                            saveAuthToken(null);
                            Toast.makeText(context, context.getString(R.string.login_first), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<TokenAuthResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }
}
