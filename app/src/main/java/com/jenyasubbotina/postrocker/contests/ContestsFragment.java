package com.jenyasubbotina.postrocker.contests;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsFragment extends Fragment {
    ArrayList<ContestsModel> contests = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contests_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            getContests();
        }
        return view;
    }

    public void getContests() {
        NetworkService.getInstance()
                .getJSONApi()
                .getAllContests()
                .enqueue(new Callback<ContestsResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<ContestsResponsePojo> call,
                                           @NotNull Response<ContestsResponsePojo> response) {
                        assert response.body() != null;
                        ArrayList<ContestsPojo> c = response.body().getContests();
                        for (ContestsPojo cp : c) {
                            contests.add(new ContestsModel(cp.getId(), cp.getTitle(),
                                    cp.getDescription(), cp.getStartTime(), cp.getDuration()));
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                        recyclerView.setAdapter(new ContestsAdapter(contests));
                    }

                    @Override
                    public void onFailure(@NotNull Call<ContestsResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }
}