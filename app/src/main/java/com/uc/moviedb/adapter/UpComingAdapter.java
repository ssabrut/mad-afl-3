package com.uc.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.UpComing;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder> {

    public UpComingAdapter() {}

    private Context context;
    private List<UpComing.Results> listUpComing;

    public UpComingAdapter(Context context) {
        this.context = context;
    }

    private List<UpComing.Results> getListUpComing() {
        return listUpComing;
    }

    public void setListUpComing(List<UpComing.Results> listUpComing) {
        this.listUpComing = listUpComing;
    }

    @NonNull
    @Override
    public UpComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_up_coming, parent, false);
        return new UpComingAdapter.UpComingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingViewHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.up_coming_card_img);
    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public class UpComingViewHolder extends RecyclerView.ViewHolder {

        private ImageView up_coming_card_img;

        public UpComingViewHolder(@NonNull View itemView) {
            super(itemView);
            up_coming_card_img = itemView.findViewById(R.id.up_coming_card_img);
        }
    }
}
