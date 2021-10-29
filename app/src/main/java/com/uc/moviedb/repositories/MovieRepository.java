package com.uc.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Movie;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository repository;

    private MovieRepository(){}

    public static MovieRepository getInstance(){
        if(repository == null){
            repository = new MovieRepository();
        }
        return repository;
    }

    public MutableLiveData<Movie> getMovieData(String movieId){
        final MutableLiveData<Movie> result = new MutableLiveData<>();

        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<NowPlaying> getNowPlayingData(){
        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();

        ApiService.endPoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

        return result;
    }
}
