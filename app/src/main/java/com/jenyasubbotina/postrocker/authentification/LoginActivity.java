package com.jenyasubbotina.postrocker.authentification;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
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

public class LoginActivity extends Fragment {

    EditText username, password;
    Button login, back;
    SessionManager sm;
    boolean running = true;
    NavController navController;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_login, container, false);
        init();
        runThread();
        login.setOnClickListener(v -> {
            String name, pass;
            name = username.getText().toString().trim();
            pass = password.getText().toString().trim();
            View view = requireActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            signIn(name, pass);
        });
        return v;
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
                            Snackbar.make(password, getString(R.string.signed_in), Snackbar.LENGTH_SHORT).show();
                            sm.saveAuthToken(response.body().getAccessToken());
                            sm.saveRefreshToken(response.body().getRefreshToken());
                            navController.navigate(R.id.cabinetFragment);
                        } else if (response.code() == 401) {
                            refreshToken(sm.fetchRefreshToken());
                        } else if (response.code() == 400) {
                            sm.saveRefreshToken(null);
                            sm.saveAuthToken(null);
                            Toast.makeText(requireContext(),
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
        navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        login = v.findViewById(R.id.login);
        sm = new SessionManager(requireContext());
        back = v.findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            running = false;
            navController.navigate(R.id.navigationFragment);
        });
    }

    private void runThread() {
        new Thread() {
            public void run() {
                while (running) {
                    try {
                        requireActivity().runOnUiThread(() -> login.setEnabled(username.getText().toString().length() > 0 &&
                                password.getText().toString().length() > 0));
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
