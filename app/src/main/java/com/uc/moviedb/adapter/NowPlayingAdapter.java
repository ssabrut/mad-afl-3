package com.uc.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.view.activities.MovieDetailsActivity;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {
    public NowPlayingAdapter() {
    }

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;

    public NowPlayingAdapter(Context context){
        this.context = context;
    }

    private List<NowPlaying.Results> getListNowPlaying(){
        return listNowPlaying;
    }

    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying = listNowPlaying;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new NowPlayingAdapter.NowPlayingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.NowPlayingViewHolder holder, int position) {
        final NowPlaying.Results results = getListNowPlaying().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(results.getRelease_date());
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
                bundle.putString("movie_id", "" + results.getId());
                Navigation.findNavController(view).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListNowPlaying().size();
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public NowPlayingViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_nowplaying);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_nowplaying);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_nowplaying);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_nowplaying);
            cv = itemView.findViewById(R.id.cv_card_nowplaying);
        }
    }
}
