package com.jenyasubbotina.postrocker.contests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jenyasubbotina.postrocker.Constants;
import com.jenyasubbotina.postrocker.MainActivity;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.RecyclerItemClickListener;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsFragment extends Fragment {
    ArrayList<ContestsModel> contests = new ArrayList<>();
    RecyclerView recyclerView;
    NavController navController;
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton fab;
    NestedScrollView scrollView;
    RecyclerItemClickListener listener;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_contests_list, container, false);
        init();
        getContests();
        listener = new RecyclerItemClickListener(requireContext(),
                recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.CONTEST_ID, contests.get(position).getId());
                navController.navigate(R.id.singleContestActivity, bundle);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });
        return v;
    }

    public void getContests() {
        NetworkService.getInstance()
                .getJSONApi()
                .getAllContests()
                .enqueue(new Callback<ContestsResponsePojo>() {
                    @Override
                    public void onResponse(@NotNull Call<ContestsResponsePojo> call,
                                           @NotNull Response<ContestsResponsePojo> response) {
                        assert response.body() != null;
                        ArrayList<ContestsPojo> c = response.body().getContests();
                        contests.clear();
                        for (ContestsPojo cp : c) {
                            contests.add(new ContestsModel(cp.getId(), cp.getTitle(),
                                    cp.getDescription(), cp.getStartTime(), cp.getDuration()));
                        }
                        recyclerView.addOnItemTouchListener(listener);
                        recyclerView.setAdapter(new ContestsAdapter(contests));
                        recyclerView.scheduleLayoutAnimation();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ContestsResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }

    public void init() {
        recyclerView = v.findViewById(R.id.contests);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fab = v.findViewById(R.id.btn_to_top);
        scrollView = v.findViewById(R.id.scrollView);
        refreshLayout = v.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(() -> {
            getContests();
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
    }
}
