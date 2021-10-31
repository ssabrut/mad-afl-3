package com.uc.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Movie;

import java.util.List;

public class ProductionCompaniesAdapter extends RecyclerView.Adapter<ProductionCompaniesAdapter.ProductionCompaniesViewHolder> {

    public ProductionCompaniesAdapter() {

    }

    private Context context;
    private List<Movie.ProductionCompanies> listProductionCompanies;

    public ProductionCompaniesAdapter(Context context) {
        this.context = context;
    }

    private List<Movie.ProductionCompanies> getListProductionCompanies() {
        return listProductionCompanies;
    }

    public void setListProductionCompanies(List<Movie.ProductionCompanies> listProductionCompanies) {
        this.listProductionCompanies = listProductionCompanies;
    }

    @NonNull
    @Override
    public ProductionCompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_production_companies, parent, false);
        return new ProductionCompaniesAdapter.ProductionCompaniesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompaniesViewHolder holder, int position) {
        final Movie.ProductionCompanies results = getListProductionCompanies().get(position);
        Glide.with(context).load(Const.IMG_URL + results.getLogo_path()).into(holder.card_production_companies_image);

        holder.card_production_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, results.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListProductionCompanies().size();
    }

    public class ProductionCompaniesViewHolder extends RecyclerView.ViewHolder {

        private ImageView card_production_companies_image;
        private CardView card_production_companies;

        public ProductionCompaniesViewHolder(@NonNull View itemView) {
            super(itemView);
            card_production_companies_image = itemView.findViewById(R.id.card_production_companies_image);
            card_production_companies = itemView.findViewById(R.id.card_production_companies);
        }
    }
}
