package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.uc.moviedb.R;
import com.uc.moviedb.adapter.NowPlayingAdapter;
import com.uc.moviedb.adapter.PopularAdapter;
import com.uc.moviedb.model.Genre;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.Popular;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class NowPlayingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView now_playing_fragment_rv, popular_fragment_rv;
    private MovieViewModel viewModel;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        now_playing_fragment_rv = view.findViewById(R.id.now_playing_fragment_rv);
        popular_fragment_rv = view.findViewById(R.id.popular_fragment_rv);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getNowPlaying();
        viewModel.getResultNowPlaying().observe(getViewLifecycleOwner(), showNowPlaying);
        viewModel.getPopular();
        viewModel.getResultPopular().observe(getViewLifecycleOwner(), showPopular);
        return view;
    }

    public StringBuilder getMovieGenre(List<Integer> genre) {
        StringBuilder genreSet = new StringBuilder();
        viewModel.getGenre(genre);
        viewModel.getResultMovieGenre().observe(this, new Observer<List<Genre.Genres>>() {
            @Override
            public void onChanged(List<Genre.Genres> genres) {
                for (int i = 0; i < genres.size(); i++) {
                    if (i != genres.size() - 1) {
                        genreSet.append(genres.get(i).getName() + ", ");
                    } else {
                        genreSet.append(genres.get(i).getName());
                    }
                }
            }
        });

        return genreSet;
    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            now_playing_fragment_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            NowPlayingAdapter adapter = new NowPlayingAdapter(getActivity().getApplicationContext());
            adapter.setListNowPlaying(nowPlaying.getResults());
            now_playing_fragment_rv.setAdapter(adapter);
        }
    };

    private Observer<Popular> showPopular = new Observer<Popular>() {
        @Override
        public void onChanged(Popular popular) {
            popular_fragment_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            PopularAdapter adapter = new PopularAdapter(getActivity().getApplicationContext());
            adapter.setListPopular(popular.getResults());
            popular_fragment_rv.setAdapter(adapter);
        }
    };
}