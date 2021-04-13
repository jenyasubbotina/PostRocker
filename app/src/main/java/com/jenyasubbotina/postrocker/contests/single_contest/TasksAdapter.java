package com.jenyasubbotina.postrocker.contests.single_contest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jenyasubbotina.postrocker.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final ArrayList<TaskModel> items;
    private static final ArrayList<ToggleButton> buttons = new ArrayList<>();
    private final Context mContext;
    public static TaskClick listener;
    public static ArrayList<Boolean> pressed = new ArrayList<>();

    public TasksAdapter(ArrayList<TaskModel> c, Context context, TaskClick l) {
        this.items = c;
        this.mContext = context;
        listener = l;
        buttons.clear();
        pressed.clear();
        pressed.addAll(Collections.nCopies(items.size(), false));
    }

    @NotNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_single_contest_task_item, parent, false);
        return new TasksAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TasksAdapter.ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getTitle());
        if (!buttons.contains(holder.name))
            buttons.add(holder.name);
        if (pressed.get(position)) {
            holder.name.setBackgroundResource(R.drawable.task_active);
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.name.setBackgroundResource(R.drawable.task_unactive);
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ToggleButton name;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.task_name);
            name.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                int i = 0;
                if (isChecked) {
                    for (ToggleButton b : buttons) {
                        b.setChecked(false);
                        pressed.set(i, false);
                        i++;
                    }
                    name.setChecked(true);
                    pressed.set(getAdapterPosition(), true);
                    listener.onClick(compoundButton, getAdapterPosition());
                }
            });
        }
    }
}
