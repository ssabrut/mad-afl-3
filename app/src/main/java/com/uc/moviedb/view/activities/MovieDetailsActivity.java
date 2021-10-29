package com.uc.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text, lbl_release, lbl_overview;
    private ImageView lbl_image;
    private String movie_id = "", title = "", overview = "", release_date = "", poster_path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        title = intent.getStringExtra("title");
        overview = intent.getStringExtra("overview");
        release_date = intent.getStringExtra("release_date");
        poster_path = intent.getStringExtra("poster_path");
        lbl_text = findViewById(R.id.lbl_text);
        lbl_image = findViewById(R.id.lbl_image);
        lbl_release = findViewById(R.id.lbl_release);
        lbl_overview = findViewById(R.id.lbl_overview);
        Glide.with(this).load(Const.IMG_URL + poster_path).into(lbl_image);
        lbl_text.setText(title);
        lbl_release.setText(release_date);
        lbl_overview.setText(overview);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}