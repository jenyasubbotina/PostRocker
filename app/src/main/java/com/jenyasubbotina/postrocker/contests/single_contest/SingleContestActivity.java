package com.jenyasubbotina.postrocker.contests.single_contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jenyasubbotina.postrocker.MainActivity;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.contests.ContestsModel;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.TasksPojo;
import com.jenyasubbotina.postrocker.pojo.TasksResponsePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleContestActivity extends AppCompatActivity implements TaskClick {
    ArrayList<TaskModel> tasks = new ArrayList<>();
    RecyclerView rvTasks;
    TextView contestName, contestDescription;
    Long id;
    TaskClick lis;
    TasksAdapter adapter;
    TextView taskName, taskTimeLimit, taskMemoryLimit, taskDescription;
    TableLayout taskIO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contest);
        getSupportActionBar().hide();
        id = getIntent().getExtras().getLong(MainActivity.CONTEST_ID);
        init();
        getContestInfo(id);
        rvTasks.setLayoutManager(new LinearLayoutManager(SingleContestActivity.this));
        getTasksOfContest(id);
    }

    public void init() {
        rvTasks = findViewById(R.id.contest_tasks);
        contestName = findViewById(R.id.contest_name);
        contestDescription = findViewById(R.id.contest_description);
        taskName = findViewById(R.id.task_name);
        taskTimeLimit = findViewById(R.id.task_time_limit);
        taskMemoryLimit = findViewById(R.id.task_memory_limit);
        taskDescription = findViewById(R.id.task_description);
        taskIO = findViewById(R.id.task_io_example);
        lis = this;
    }

    public void getContestInfo(Long id) {
        NetworkService.getInstance()
                .getJSONApi()
                .getContestById(id)
                .enqueue(new Callback<ContestsPojo>() {
                    @Override
                    public void onResponse(@NotNull Call<ContestsPojo> call,
                                           @NotNull Response<ContestsPojo> response) {
                        ContestsPojo pojo = response.body();
                        ContestsModel contest = new ContestsModel(Objects.requireNonNull(pojo).getId(),
                                pojo.getTitle(), pojo.getDescription(), pojo.getStartTime(), pojo.getDuration());
                        contestName.setText(contest.getTitle());
                        contestDescription.setText(contest.getDescription());
                    }

                    @Override
                    public void onFailure(@NotNull Call<ContestsPojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void setupTaskInfo(TaskModel task) {
        taskName.setText(task.getTitle());
        taskDescription.setText(task.getContent());
        taskTimeLimit.setText(getString(R.string.time_limit, task.getTl()));
        taskMemoryLimit.setText(getString(R.string.memory_limit, task.getMl()/1024/1024));
    }

    public void getTasksOfContest(Long id) {
        NetworkService.getInstance()
                .getJSONApi()
                .getTasksByContest(id)
                .enqueue(new Callback<TasksResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TasksResponsePojo> call,
                                           @NotNull Response<TasksResponsePojo> response) {
                        ArrayList<TasksPojo> t = Objects.requireNonNull(response.body()).getTasks();
                        tasks.clear();
                        for (TasksPojo tp : t) {
                            tasks.add(new TaskModel(tp.getId(), tp.getTitle(), tp.getContent(), tp.getContest(),
                                    tp.getTl(), tp.getMl(), tp.getSamples()));
                        }
                        System.out.println(tasks);
                        adapter = new TasksAdapter(tasks, SingleContestActivity.this, lis);
                        rvTasks.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NotNull Call<TasksResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    @Override
    public void onClick(View view, int position) {
        setupTaskInfo(tasks.get(position));
        adapter.notifyDataSetChanged();
    }
}
