package com.jenyasubbotina.postrocker.tasks;

import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.contests.single_contest.TaskModel;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.TasksPojo;
import com.jenyasubbotina.postrocker.pojo.TasksResponsePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    NestedScrollView scrollView;
    ArrayList<TasksModel> tasks = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tasks, container, false);
        init();
        getTasks();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTasks();
                refreshLayout.setRefreshing(false);
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY > oldScrollY)
                        fab.show();
                    else
                        fab.hide();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.fling(0);
                scrollView.smoothScrollTo(0, 0);
            }
        });

        return v;
    }

    public void init() {
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fab = v.findViewById(R.id.btn_to_top);
        scrollView = v.findViewById(R.id.scrollView);
        refreshLayout = v.findViewById(R.id.refresh);
    }

    public void getTasks() {
        NetworkService.getInstance()
                .getJSONApi()
                .getAllTasks()
                .enqueue(new Callback<TasksResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TasksResponsePojo> call, @NotNull Response<TasksResponsePojo> response) {
                        ArrayList<TasksPojo> tasksPojo = response.body().getTasks();
                        tasks.clear();
                        for (TasksPojo t : tasksPojo) {
                            tasks.add(new TasksModel(t.getId(), t.getTitle(), t.getContent(),
                                    t.getContest(), t.getTl(), t.getMl(), t.getSamples()));
                        }
                        recyclerView.setAdapter(new TasksAdapter(tasks));
                        recyclerView.scheduleLayoutAnimation();
                    }

                    @Override
                    public void onFailure(@NotNull Call<TasksResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }
}
