package com.jenyasubbotina.postrocker.tasks;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jenyasubbotina.postrocker.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final ArrayList<TasksModel> tasks;

    public TasksAdapter(ArrayList<TasksModel> items) {
        tasks = items;
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
