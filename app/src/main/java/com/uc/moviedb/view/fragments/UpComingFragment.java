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

import com.uc.moviedb.R;
import com.uc.moviedb.adapter.UpComingAdapter;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.viewmodel.MovieViewModel;

public class UpComingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView up_coming_fragment_rv;
    private MovieViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getUpComing();
        viewModel.getResultUpComing().observe(getViewLifecycleOwner(), showUpComing);

        return view;
    }

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            up_coming_fragment_rv.setLayoutManager(layoutManager);
            UpComingAdapter adapter = new UpComingAdapter(getActivity().getApplicationContext());
            adapter.setListUpComing(upComing.getResults());
            up_coming_fragment_rv.setAdapter(adapter);
        }
    };
}