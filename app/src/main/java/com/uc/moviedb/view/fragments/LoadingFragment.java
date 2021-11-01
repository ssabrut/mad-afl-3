package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.moviedb.R;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.Popular;
import com.uc.moviedb.model.UpComing;

public class LoadingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public LoadingFragment() {
        // Required empty public constructor
    }

    public static LoadingFragment newInstance(String param1, String param2) {
        LoadingFragment fragment = new LoadingFragment();
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
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        NowPlaying.Results movie = getArguments().getParcelable("movie");
        Popular.Results popular = getArguments().getParcelable("popular");
        UpComing.Results upcoming = getArguments().getParcelable("upcoming");

        if (movie != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movie", movie);
                    Navigation.findNavController(view).navigate(R.id.action_loadingFragment_to_movieDetailsFragment, bundle);
                }
            }, 1000);
        } else if (popular != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("popular", popular);
                    Navigation.findNavController(view).navigate(R.id.action_loadingFragment_to_movieDetailsFragment, bundle);
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("upcoming", upcoming);
                    Navigation.findNavController(view).navigate(R.id.action_loadingFragment_to_movieDetailsFragment, bundle);
                }
            }, 1000);
        }
        return view;
    }
}