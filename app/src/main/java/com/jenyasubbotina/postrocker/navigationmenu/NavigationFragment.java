package com.jenyasubbotina.postrocker.navigationmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.jenyasubbotina.postrocker.MainActivity;
import com.jenyasubbotina.postrocker.R;

public class NavigationFragment extends Fragment {
    RecyclerView subcategories;
    NavController navController;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_navigation, container, false);
        init();
        return v;
    }

    public void init() {
        navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
        subcategories = v.findViewById(R.id.rv_subcategories);
        subcategories.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        subcategories.setAdapter(new SubcategoriesAdapter(requireContext(), navController));
    }
}
