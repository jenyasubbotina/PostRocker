package com.jenyasubbotina.postrocker.contests;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jenyasubbotina.postrocker.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        Long secs = items.get(position).getDuration();
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                secs / 3600, (secs % 3600) / 60, secs % 60);
        holder.duration.setText(hms);

        Calendar cal = Calendar.getInstance();
        long milliDiff = cal.get(Calendar.ZONE_OFFSET);
        String [] ids = TimeZone.getAvailableIDs();
        String name = null;
        for (String id : ids) {
            TimeZone tz = TimeZone.getTimeZone(id);
            if (tz.getRawOffset() == milliDiff) {
                name = id;
                break;
            }
        }
        TimeZone tz = TimeZone.getTimeZone(name);
        ZoneOffset offset = ZonedDateTime.now(tz.toZoneId())
                .getOffset();
        Date time = new java.util.Date((long)items.get(position).getStartTime()*1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        formatter.setTimeZone(TimeZone.getTimeZone(name));
        String strDate= formatter.format(time);
        holder.time.setText(strDate + " " + offset.toString());
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
