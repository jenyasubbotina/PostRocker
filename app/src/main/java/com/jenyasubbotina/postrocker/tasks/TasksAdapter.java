package com.jenyasubbotina.postrocker.tasks;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.contests.single_contest.TaskModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final ArrayList<TaskModel> tasks;
    private final NavController navController;
    public static final String TASK_ID = "TASK_ID";

    public TasksAdapter(ArrayList<TaskModel> items, NavController controller) {
        tasks = items;
        this.navController = controller;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tasks_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getTitle());
        holder.description.setText(tasks.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putLong(TASK_ID, tasks.get(position).getId());
                navController.navigate(R.id.singleTask, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView description;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.task_name);
            description = view.findViewById(R.id.task_description);
        }
    }
}
