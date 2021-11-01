package com.uc.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.view.activities.MovieDetailsActivity;
import com.uc.moviedb.view.fragments.NowPlayingFragment;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {
    public NowPlayingAdapter() {
    }

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;
    public static boolean loading = false;
    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_ITEM = 1;

    public NowPlayingAdapter(Context context){
        this.context = context;
    }

    public List<NowPlaying.Results> getListNowPlaying(){
        return listNowPlaying;
    }

    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying = listNowPlaying;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
            return new NowPlayingAdapter.NowPlayingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.NowPlayingViewHolder holder, int position) {
        final NowPlaying.Results results = getListNowPlaying().get(position);
        if (results != null) {
            holder.lbl_title.setText(results.getTitle());
            holder.card_now_playing_vote_avg.setText(String.valueOf(results.getVote_average()));
            Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
//                intent.putExtra("movie_id", "" + results.getId());
//                intent.putExtra("title", "" + results.getTitle());
//                intent.putExtra("overview", "" + results.getOverview());
//                intent.putExtra("release_date", "" + results.getRelease_date());
//                intent.putExtra("poster_path", "" + results.getPoster_path());
//                view.getContext().startActivity(intent);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movie", results);
                    Navigation.findNavController(view).navigate(R.id.action_nowPlayingFragment_to_loadingFragment, bundle);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listNowPlaying.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getListNowPlaying().size();
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder {

        ImageView img_poster;
        TextView lbl_title, card_now_playing_vote_avg;
        CardView cv;

        public NowPlayingViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_nowplaying);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_nowplaying);
            card_now_playing_vote_avg = itemView.findViewById(R.id.card_now_playing_vote_avg);
            cv = itemView.findViewById(R.id.cv_card_nowplaying);
        }
    }

    public class LoadingHolder extends NowPlayingViewHolder {
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
