package com.jenyasubbotina.postrocker.authentification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.SessionManager;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.CreateUserBodyPojo;
import com.jenyasubbotina.postrocker.pojo.CreateUserResponsePojo;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends Fragment {

    Button registration, back;
    EditText username, password, userEmail;
    boolean running = true;
    NavController navController;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_registration, container, false);
        init();
        runThread();
        registration.setOnClickListener(v -> {
            String name, email, pass;
            name = username.getText().toString().trim();
            email = userEmail.getText().toString().trim();
            pass = password.getText().toString().trim();
            View view = requireActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            signUp(name, email, pass);
        });
        return v;
    }

    public void init() {
        navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
        registration = v.findViewById(R.id.registration);
        username = v.findViewById(R.id.username);
        userEmail = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);
        back = v.findViewById(R.id.btn_back);
        back.setOnClickListener(v -> {
            running = false;
            navController.navigate(R.id.navigationFragment);
        });
    }

    public void signUp(String u, String e, String p) {
        NetworkService.getInstance()
                .getJSONApi()
                .createUser(new CreateUserBodyPojo(u, e, p))
                .enqueue(new Callback<CreateUserResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<CreateUserResponsePojo> call,
                                           @NotNull Response<CreateUserResponsePojo> response) {
                        if (response.code() == 201 && response.body() != null) {
                            SessionManager sm = new SessionManager(requireContext());
                            sm.saveUserId(response.body().getId());
                            Snackbar.make(password, getString(R.string.signed_up), Snackbar.LENGTH_SHORT).show();
                            running = false;
                            navController.navigate(R.id.loginActivity);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<CreateUserResponsePojo> call, @NotNull Throwable t) {


                    }
                });
    }

    private void runThread() {
        new Thread() {
            public void run() {
                while (running) {
                    try {
                        requireActivity().runOnUiThread(() -> registration.setEnabled(username.getText().toString().length() > 0 &&
                                password.getText().toString().length() > 0 && userEmail.getText().toString().length() > 0));
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}