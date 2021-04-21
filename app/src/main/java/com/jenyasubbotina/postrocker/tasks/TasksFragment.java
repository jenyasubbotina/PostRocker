package com.jenyasubbotina.postrocker.tasks;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.contests.single_contest.TaskModel;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.TasksPojo;
import com.jenyasubbotina.postrocker.pojo.TasksResponsePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    NestedScrollView scrollView;
    ArrayList<TaskModel> tasks = new ArrayList<>();
    FloatingActionButton fab;
    EditText find;
    ProgressBar progressBar;
    TextView notFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tasks, container, false);
        init();
        getTasks();

        find.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                getTasksByWord(find.getText().toString().trim());
                return true;
            }
            return false;
        });

        refreshLayout.setOnRefreshListener(() -> {
            getTasks();
            refreshLayout.setRefreshing(false);
        });

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if (scrollY > oldScrollY)
                    fab.show();
                else
                    fab.hide();
            }
        });

        fab.setOnClickListener(view -> {
            scrollView.fling(0);
            scrollView.smoothScrollTo(0, 0);
        });

        return v;
    }

    public void init() {
        recyclerView = v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fab = v.findViewById(R.id.btn_to_top);
        scrollView = v.findViewById(R.id.scrollView);
        refreshLayout = v.findViewById(R.id.refresh);
        find = v.findViewById(R.id.find_task);
        progressBar = v.findViewById(R.id.progress);
        notFound = v.findViewById(R.id.not_found);
    }

    public void updateRecyclerView(ArrayList<TaskModel> tasksModelArrayList) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
        recyclerView.setAdapter(new TasksAdapter(tasksModelArrayList,  navController));
        progressBar.setVisibility(View.INVISIBLE);
        if (tasksModelArrayList.size() > 0) {
            notFound.setVisibility(View.INVISIBLE);
        } else {
            notFound.setVisibility(View.VISIBLE);
        }
        recyclerView.scheduleLayoutAnimation();
    }

    public void getTasks() {
        progressBar.setVisibility(View.VISIBLE);
        NetworkService.getInstance()
                .getJSONApi()
                .getAllTasks()
                .enqueue(new Callback<TasksResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TasksResponsePojo> call,
                                           @NotNull Response<TasksResponsePojo> response) {
                        assert response.body() != null;
                        ArrayList<TasksPojo> tasksPojo = response.body().getTasks();
                        tasks.clear();
                        for (TasksPojo t : tasksPojo) {
                            tasks.add(new TaskModel(t.getId(), t.getTitle(), t.getContent(),
                                    t.getContest(), t.getTl(), t.getMl(), t.getSamples()));
                        }
                        updateRecyclerView(tasks);
                    }

                    @Override
                    public void onFailure(@NotNull Call<TasksResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void getTasksByWord(String word) {
        progressBar.setVisibility(View.VISIBLE);
        NetworkService.getInstance()
                .getJSONApi()
                .getTaskByKeyword(word)
                .enqueue(new Callback<TasksResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TasksResponsePojo> call,
                                           @NotNull Response<TasksResponsePojo> response) {
                        ArrayList<TasksPojo> tasksPojo = Objects.requireNonNull(response.body()).getTasks();
                        tasks.clear();
                        for (TasksPojo t : tasksPojo) {
                            tasks.add(new TaskModel(t.getId(), t.getTitle(), t.getContent(),
                                    t.getContest(), t.getTl(), t.getMl(), t.getSamples()));
                        }
                        updateRecyclerView(tasks);
                    }

                    @Override
                    public void onFailure(@NotNull Call<TasksResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }
}
