package com.uc.moviedb.view.fragments;

import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Genre;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.viewmodel.MovieViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ImageView movie_detail_fragment_backdrop;
    private TextView movie_detail_fragment_title, movie_detail_fragment_year, movie_detail_fragment_vote_avg, movie_detail_fragment_genre;
    private List<Integer> genre;
    private MovieViewModel viewModel;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        NowPlaying.Results movie = getArguments().getParcelable("movie");
        initComponent(view);
        Glide.with(getActivity().getApplicationContext()).load(Const.IMG_URL + movie.getBackdrop_path()).into(movie_detail_fragment_backdrop);
        movie_detail_fragment_title.setText(movie.getTitle());
        movie_detail_fragment_year.setText(movie.getRelease_date().substring(0, 4));
        movie_detail_fragment_vote_avg.setText(String.valueOf(movie.getVote_average()));
        genre = movie.getGenre_ids();
        viewModel.getGenre(genre);
        viewModel.getResultMovieGenre().observe(getViewLifecycleOwner(), new Observer<List<Genre.Genres>>() {
            @Override
            public void onChanged(List<Genre.Genres> listGenre) {
                StringBuilder foo = new StringBuilder();

                for (int i = 0; i < listGenre.size(); i++) {
                    if (i != listGenre.size() - 1) {
                        foo.append(listGenre.get(i).getName() + ", ");
                    } else {
                        foo.append(listGenre.get(i).getName());
                    }
                }

                movie_detail_fragment_genre.setText(foo);
            }
        });
        return view;
    }

    private void initComponent(View view) {
        movie_detail_fragment_backdrop = view.findViewById(R.id.movie_detail_fragment_backdrop);
        movie_detail_fragment_title = view.findViewById(R.id.movie_detail_fragment_title);
        movie_detail_fragment_year = view.findViewById(R.id.movie_detail_fragment_year);
        movie_detail_fragment_vote_avg = view.findViewById(R.id.movie_detail_fragment_vote_avg);
        movie_detail_fragment_genre = view.findViewById(R.id.movie_detail_fragment_genre);
        genre = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
    }
}