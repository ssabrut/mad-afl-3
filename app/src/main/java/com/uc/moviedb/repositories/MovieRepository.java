package com.uc.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Genre;
import com.uc.moviedb.model.Movie;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.Popular;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository repository;

    private MovieRepository() {
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            repository = new MovieRepository();
        }
        return repository;
    }

    public MutableLiveData<Movie> getMovieData(String movieId) {
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

    private List<NowPlaying.Results> nowPlayingList = new ArrayList<>();

    public MutableLiveData<List<NowPlaying.Results>> getNowPlayingData(int page) {
        final MutableLiveData<List<NowPlaying.Results>> result = new MutableLiveData<>();

        ApiService.endPoint().getNowPlaying(page, Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                nowPlayingList.addAll(response.body().getResults());
                result.setValue(nowPlayingList);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

        return result;
    }

    private List<UpComing.Results> upComingList = new ArrayList<>();

    public MutableLiveData<List<UpComing.Results>> getUpcomingData(int page) {
        final MutableLiveData<List<UpComing.Results>> result = new MutableLiveData<>();

        ApiService.endPoint().getUpComing(page, Const.API_KEY).enqueue(new Callback<UpComing>() {
            @Override
            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
                upComingList.addAll(response.body().getResults());
                result.setValue(upComingList);
            }

            @Override
            public void onFailure(Call<UpComing> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return result;
    }

    public MutableLiveData<Popular> getPopularData() {
        final MutableLiveData<Popular> result = new MutableLiveData<>();

        ApiService.endPoint().getPopular(Const.API_KEY).enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<List<Genre.Genres>> getMovieGenre(List<Integer> genreId) {
        final MutableLiveData<List<Genre.Genres>> result = new MutableLiveData<>();

        ApiService.endPoint().getGenre(Const.API_KEY).enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                List<Genre.Genres> foo = new ArrayList<>();
                for (int i : genreId) {
                    for (Genre.Genres movieGenre : response.body().getGenres()) {
                        if (movieGenre.getId() == i) {
                            foo.add(movieGenre);
                        }
                    }
                }

                result.setValue(foo);
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<List<Movie.ProductionCompanies>> getMovieProductionCompanies(List<Integer> companyId, String movieId) {
        final MutableLiveData<List<Movie.ProductionCompanies>> result = new MutableLiveData();

        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

        return result;
    }
}
