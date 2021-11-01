package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;

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
    private LinearLayoutManager layoutManager;
    private boolean isScrolling;
    private int currentItem, totalItem, scrolledItem, page;
    private NowPlayingAdapter adapter;

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
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        adapter = new NowPlayingAdapter(getActivity().getApplicationContext());
        isScrolling = false;
        page = 1;
        now_playing_fragment_rv = view.findViewById(R.id.now_playing_fragment_rv);
        popular_fragment_rv = view.findViewById(R.id.popular_fragment_rv);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getNowPlaying(page);
        viewModel.getResultNowPlaying().observe(getViewLifecycleOwner(), new Observer<List<NowPlaying.Results>>() {
            @Override
            public void onChanged(List<NowPlaying.Results> results) {
                now_playing_fragment_rv.setLayoutManager(layoutManager);
                adapter.setListNowPlaying(results);
                adapter.getListNowPlaying().add(null);
                now_playing_fragment_rv.setAdapter(adapter);

                for (int i = 0; i < results.size(); i++) {
                    if (adapter.getListNowPlaying().get(i) == null) {
                        adapter.getListNowPlaying().remove(i);
                    }
                }
            }
        });

        now_playing_fragment_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    viewModel.getNowPlaying(page);
                    viewModel.getResultNowPlaying().observe(getViewLifecycleOwner(), new Observer<List<NowPlaying.Results>>() {
                        @Override
                        public void onChanged(List<NowPlaying.Results> results) {
                            adapter.setListNowPlaying(results);
                            adapter.getListNowPlaying().add(null);
                            adapter.notifyDataSetChanged();
                            isScrolling = true;

                            for (int i = adapter.getListNowPlaying().size() - results.size(); i < adapter.getListNowPlaying().size(); i++) {
                                if (adapter.getListNowPlaying().get(i) == null) {
                                    adapter.getListNowPlaying().remove(i);
                                }
                            }
                        }
                    });
                }
            }
        });

        viewModel.getPopular();
        viewModel.getResultPopular().observe(getViewLifecycleOwner(), showPopular);
        return view;
    }

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