package com.jenyasubbotina.postrocker.navigationmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jenyasubbotina.postrocker.R;

import org.jetbrains.annotations.NotNull;

public class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.ViewHolder> {

    private final Context context;
    private final NavController navController;
    int[] names = {R.string.task, R.string.contests, R.string.rating, R.string.faq};
    int[] images = {R.drawable.images, R.drawable.images1, R.drawable.images2,
            R.drawable.images, R.drawable.images1, R.drawable.images2};

    private final int CONTEST_STRING = R.string.contests;
    private final int TASKS_STRING = R.string.task;


    public SubcategoriesAdapter(Context c, NavController controller) {
        this.context = c;
        this.navController = controller;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(names[position]);
        holder.image.setImageResource(images[position]);
        holder.card.setId(names[position]);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                int navigateTo;
                switch (v.getId()) {
                    case CONTEST_STRING:
                        navigateTo = R.id.contestsFragment;
                        break;
                    case TASKS_STRING:
                        navigateTo = R.id.tasksFragment;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
                navController.navigate(navigateTo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView image;
        public final CardView card;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.background);
            card = view.findViewById(R.id.card);
        }
    }
}
