package com.uc.moviedb.adapter;

import android.content.Context;
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
        holder.up_coming_card_title.setText(results.getTitle());
        holder.up_coming_caard_release_date.setText("(" + results.getRelease_date().substring(0, 4) + ")");

        holder.up_coming_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("upcoming", results);
                Navigation.findNavController(view).navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public class UpComingViewHolder extends RecyclerView.ViewHolder {

        private ImageView up_coming_card_img;
        private TextView up_coming_card_title, up_coming_caard_release_date;
        private CardView up_coming_card;

        public UpComingViewHolder(@NonNull View itemView) {
            super(itemView);
            up_coming_card_img = itemView.findViewById(R.id.up_coming_card_img);
            up_coming_card_title = itemView.findViewById(R.id.up_coming_card_title);
            up_coming_caard_release_date = itemView.findViewById(R.id.up_coming_caard_release_date);
            up_coming_card = itemView.findViewById(R.id.up_coming_card);
        }
    }
}
