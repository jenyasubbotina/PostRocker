package com.jenyasubbotina.postrocker.tasks.single_task;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.contests.single_contest.TaskModel;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.TasksPojo;
import com.jenyasubbotina.postrocker.tasks.TasksAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleTask extends AppCompatActivity {

    TextView taskName, taskTimeLimit, taskMemoryLimit, taskDescription;
    TableLayout taskIO;
    Button tasksMenu;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        init();
        id = getIntent().getLongExtra(TasksAdapter.TASK_ID, 0);
        getTask(id);
    }

    public void init() {
        taskName = findViewById(R.id.task_name);
        taskTimeLimit = findViewById(R.id.task_time_limit);
        taskMemoryLimit = findViewById(R.id.task_memory_limit);
        taskDescription = findViewById(R.id.task_description);
        taskIO = findViewById(R.id.task_io_example);
        tasksMenu = findViewById(R.id.task_menu);
    }

    public void getTask(Long id) {
        NetworkService.getInstance()
                .getJSONApi()
                .getSingleTask(id)
                .enqueue(new Callback<TasksPojo>() {
                    @Override
                    public void onResponse(@NotNull Call<TasksPojo> call,
                                           @NotNull Response<TasksPojo> response) {
                        TasksPojo r = response.body();
                        assert r != null;
                        updateTaskLayout(new TaskModel(r.getId(), r.getTitle(), r.getContent(),
                                r.getContest(), r.getTl(), r.getMl(), r.getSamples()));
                    }

                    @Override
                    public void onFailure(@NotNull Call<TasksPojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void updateTaskLayout(TaskModel task) {
        taskName.setText(task.getTitle());
        taskDescription.setText(task.getContent());
        taskTimeLimit.setText(getString(R.string.time_limit, task.getTl()));
        taskMemoryLimit.setText(getString(R.string.memory_limit, task.getMl()/1024/1024));
        List<List<String>> samples = task.getSamples();
        for (List<String> l : samples) {
            TableRow tableRow = new TableRow(SingleTask.this);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 120;
            tableRow.setLayoutParams(params);

            TextView input = new TextView(SingleTask.this);
            input.setText(l.get(0));
            input.setPadding(20, 5, 5, 5);
            input.setBackground(ContextCompat.getDrawable(SingleTask.this, R.drawable.cell_shape));

            TextView output = new TextView(SingleTask.this);
            output.setText(l.get(1));
            output.setPadding(20, 5, 5, 5);
            output.setBackground(ContextCompat.getDrawable(SingleTask.this, R.drawable.cell_shape));

            tableRow.addView(input);
            tableRow.addView(output);
            taskIO.addView(tableRow);
        }
    }
}
