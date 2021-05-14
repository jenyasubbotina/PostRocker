package com.jenyasubbotina.postrocker.cabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.SessionManager;

public class CabinetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cabinet, container, false);
        SessionManager sm = new SessionManager(getContext());
        Toast.makeText(getContext(), sm.fetchAuthToken(), Toast.LENGTH_SHORT).show();
        return v;
    }
}