package com.gingerv.newsreader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gingerv.newsreader.R;
import com.gingerv.newsreader.database.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InCategoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_category);

        // back button
        ImageButton buttonBack = findViewById(R.id.button_category_back);
        buttonBack.setOnClickListener(v -> this.finish());

        LoadNewsFromDatabase();
    }

    private void LoadNewsFromDatabase() {
        LinearLayout itemsCollectionLayout = findViewById(R.id.news_items_layout); // items collection
        LayoutInflater inflater = getLayoutInflater();

        List<News> newsList = new ArrayList<>();
        try {
            newsList = MainActivity.newsDao.findByCategory(getIntent().getIntExtra("category", 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!newsList.isEmpty()) {
            for (News news : newsList) {
                // acquire news detail
                View view = inflater.inflate(R.layout.news_item, itemsCollectionLayout, false);
                itemsCollectionLayout.addView(view);
                view.setOnClickListener(v -> {
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("newsId", news.id);
                    startActivity(intent);
                });
                TextView titleTextView = view.findViewById(R.id.news_item_title);
                titleTextView.setText(news.title);
                ImageView imageView = view.findViewById(R.id.news_item_img);
                imageView.setImageResource(news.imgLocation);
                TextView dateTextView = view.findViewById(R.id.news_item_date);
                dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(news.postDate));
            }
        } else {
            View view = inflater.inflate(R.layout.nothing_tips, itemsCollectionLayout, false);
            itemsCollectionLayout.addView(view);
        }
    }
}