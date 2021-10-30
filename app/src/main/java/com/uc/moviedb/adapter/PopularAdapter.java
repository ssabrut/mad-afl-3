package com.uc.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Popular;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    public PopularAdapter() {

    }

    private Context context;
    private List<Popular.Results> listPopular;

    public PopularAdapter(Context context) {
        this.context = context;
    }

    private List<Popular.Results> getListPopular() {
        return listPopular;
    }

    public void setListPopular(List<Popular.Results> listPopular) {
        this.listPopular = listPopular;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular, parent, false);
        return new PopularAdapter.PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        final Popular.Results results = getListPopular().get(position);
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.popular_card_img);
    }

    @Override
    public int getItemCount() {
        return getListPopular().size();
    }


    public class PopularViewHolder extends RecyclerView.ViewHolder {

        private ImageView popular_card_img;
        private CardView popular_card;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            popular_card_img = itemView.findViewById(R.id.popular_card_img);
            popular_card = itemView.findViewById(R.id.popular_card);
        }
    }
}
