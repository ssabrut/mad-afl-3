package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.uc.moviedb.R;
import com.uc.moviedb.adapter.UpComingAdapter;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class UpComingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView up_coming_fragment_rv;
    private MovieViewModel viewModel;
    LinearLayoutManager layoutManager;
    private boolean isScrolling;
    private int currentItem, totalItem, scrolledItem, page;
    UpComingAdapter adapter;

    public UpComingFragment() {
        // Required empty public constructor
    }

    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        up_coming_fragment_rv = view.findViewById(R.id.up_coming_fragment_rv);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        adapter = new UpComingAdapter(getActivity().getApplicationContext());
        isScrolling = false;
        page = 1;
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getUpComing(page);
        viewModel.getResultUpComing().observe(getViewLifecycleOwner(), new Observer<List<UpComing.Results>>() {
            @Override
            public void onChanged(List<UpComing.Results> results) {
                up_coming_fragment_rv.setLayoutManager(layoutManager);
                adapter.setListUpComing(results);
                adapter.getListUpComing().add(null);
                up_coming_fragment_rv.setAdapter(adapter);

                for (int i = 0; i < results.size(); i++) {
                    if (adapter.getListUpComing().get(i) == null) {
                        adapter.getListUpComing().remove(i);
                    }
                }
            }
        });

        up_coming_fragment_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

                isScrolling = false;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrolledItem = layoutManager.findFirstVisibleItemPosition();
                adapter.setLoading(true);

                if (!isScrolling && (currentItem + scrolledItem) >= totalItem) {
                    isScrolling = true;
                    page++;
                    viewModel.getUpComing(page);
                    viewModel.getResultUpComing().observe(getViewLifecycleOwner(), new Observer<List<UpComing.Results>>() {
                        @Override
                        public void onChanged(List<UpComing.Results> results) {
                            adapter.setListUpComing(results);
                            adapter.getListUpComing().add(null);
                            adapter.notifyDataSetChanged();
                            isScrolling = true;

                            for (int i = adapter.getListUpComing().size() - results.size(); i < adapter.getListUpComing().size(); i++) {
                                if (adapter.getListUpComing().get(i) == null) {
                                    adapter.getListUpComing().remove(i);
                                }
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}