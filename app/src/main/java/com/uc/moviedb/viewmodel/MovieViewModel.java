package com.uc.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uc.moviedb.model.Genre;
import com.uc.moviedb.model.Movie;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.Popular;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.repositories.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    private MutableLiveData<Movie> resultGetMovieById = new MutableLiveData<>();

    public void getMovieById(String movieId) {
        resultGetMovieById = repository.getMovieData(movieId);
    }

    public LiveData<Movie> getResultGetMovieById() {
        return resultGetMovieById;
    }

    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();

    public void getNowPlaying() {
        resultGetNowPlaying = repository.getNowPlayingData();
    }

    public LiveData<NowPlaying> getResultNowPlaying() {
        return resultGetNowPlaying;
    }

    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();

    public void getUpComing() {
        resultGetUpComing = repository.getUpcomingData();
    }

    public LiveData<UpComing> getResultUpComing() {
        return resultGetUpComing;
    }

    private MutableLiveData<Popular> resultGetPopular = new MutableLiveData<>();

    public void getPopular() {
        resultGetPopular = repository.getPopularData();
    }

    public LiveData<Popular> getResultPopular() {
        return resultGetPopular;
    }

    private MutableLiveData<List<Genre.Genres>> resultGetMovieGenre = new MutableLiveData<>();

    public void getGenre(List<Integer> genreId) {
        System.out.println("hi");
//        resultGetMovieGenre = repository.getMovieGenre(genreId);
    }

    public LiveData<List<Genre.Genres>> getResultMovieGenre() {
        return resultGetMovieGenre;
    }
}
