package com.jenyasubbotina.postrocker.cabinet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.SessionManager;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.UserPojo;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabinetFragment extends Fragment {

    View v;
    Button logout;
    TextView username, lastLogin, dataJoined;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cabinet, container, false);
        init();
        getInfo();
        return v;
    }

    public void init() {
        navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
        username = v.findViewById(R.id.username);
        lastLogin = v.findViewById(R.id.lastlogin);
        dataJoined = v.findViewById(R.id.joined);
        logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            SessionManager sm = new SessionManager(requireContext());
            sm.resetTokens();
            Snackbar.make(v, getString(R.string.logged_out), Snackbar.LENGTH_SHORT).show();
            navController.navigate(R.id.navigationFragment);
        });
    }

    public void getInfo() {
        SessionManager sm = new SessionManager(requireContext());
        NetworkService.getInstance()
                .getJSONApi()
                .getUserInfo(sm.getUserId())
                .enqueue(new Callback<UserPojo>() {
                    @Override
                    public void onResponse(@NotNull Call<UserPojo> call,
                                           @NotNull Response<UserPojo> response) {
                        if (response.code() == 200 && response.body() != null) {
                            setupUI(response.body());
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<UserPojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void setupUI(UserPojo user) {
        username.setText(user.getUsername());
        Date timeJoined = new java.util.Date(user.getDateJoined()*1000);
        Date timeLastLogin = new java.util.Date(user.getLastLogin()*1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String strDateJoined = formatter.format(timeJoined);
        String strLastLogin = formatter.format(timeLastLogin);
        dataJoined.setText(getString(R.string.date_joined, strDateJoined));
        lastLogin.setText(getString(R.string.last_login, strLastLogin));
    }
}
