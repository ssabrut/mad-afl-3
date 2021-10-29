package com.uc.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Movie;
import com.uc.moviedb.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button btn_hit;
    private TextView txt_show;
    private TextInputLayout movieId_input;
    private ImageView poster_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_show = findViewById(R.id.txt_show_main);
        movieId_input = findViewById(R.id.movieId_input_main);
        poster_movie = findViewById(R.id.poster_movie);

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);

        btn_hit = findViewById(R.id.btn_hit_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = movieId_input.getEditText().getText().toString().trim();
                viewModel.getMovieById(movieId);
                viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
            }
        });
    }

    private Observer<Movie> showResultMovie = new Observer<Movie>() {
        @Override
        public void onChanged(Movie movie) {
            if (movie == null) {
                txt_show.setText("Movie not found!");
            } else {
                String title = movie.getTitle();
                String img_path = movie.getPoster_path().toString();
                String full_path = Const.IMG_URL + img_path;
                Glide.with(MainActivity.this).load(full_path).into(poster_movie);
                txt_show.setText(title);
            }
        }
    };

}