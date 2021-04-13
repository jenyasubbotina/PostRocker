package com.jenyasubbotina.postrocker.contests;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jenyasubbotina.postrocker.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ContestsAdapter extends RecyclerView.Adapter<ContestsAdapter.ViewHolder> {

    private final ArrayList<ContestsModel> items;

    public ContestsAdapter(ArrayList<ContestsModel> c) {
        this.items = c;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contests_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        Long secs = items.get(position).getDuration();
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
        holder.duration.setText(hms);
        Date time = new java.util.Date((long)items.get(position).getStartTime()*1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        String strDate= formatter.format(time);
        holder.time.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView description;
        public final TextView time;
        public final TextView duration;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.contest_name);
            description = view.findViewById(R.id.contest_description);
            time = view.findViewById(R.id.contest_time);
            duration = view.findViewById(R.id.contest_duration);
        }
    }
}