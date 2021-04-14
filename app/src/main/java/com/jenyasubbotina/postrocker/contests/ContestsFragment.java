package com.jenyasubbotina.postrocker.contests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jenyasubbotina.postrocker.MainActivity;
import com.jenyasubbotina.postrocker.R;
import com.jenyasubbotina.postrocker.RecyclerItemClickListener;
import com.jenyasubbotina.postrocker.network.NetworkService;
import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsFragment extends Fragment {
    ArrayList<ContestsModel> contests = new ArrayList<>();
    RecyclerView recyclerView;
    NavController navController;
    RecyclerItemClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contests_list, container, false);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            listener = new RecyclerItemClickListener(requireContext(),
                    recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    navController = Navigation.findNavController(view);
                    Bundle bundle = new Bundle();
                    bundle.putLong(MainActivity.CONTEST_ID, contests.get(position).getId());
                    navController.navigate(R.id.singleContestActivity, bundle);
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            });
            getContests();
        }
        return view;
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
                    }

                    @Override
                    public void onFailure(@NotNull Call<ContestsResponsePojo> call, @NotNull Throwable t) {

                    }
                });
    }
}
