package com.gingerv.newsreader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gingerv.newsreader.R;
import com.gingerv.newsreader.database.News;
import com.gingerv.newsreader.file.FileHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DownloadsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        // back button
        ImageButton buttonBack = findViewById(R.id.button_downloads_back);
        buttonBack.setOnClickListener(v -> this.finish());

    }

    @Override
    protected void onResume() {
        super.onResume();
        rebuildItemsLayout();
        loadNewsFromLocal();
    }

    private void loadNewsFromLocal() {
        LinearLayout itemsCollectionLayout = findViewById(R.id.news_download_items_layout); // items collection
        LayoutInflater inflater = getLayoutInflater();
        List<News> newsList = new ArrayList<>();
        // get news download count
        Set<String> newsDownloadsFileName = MainActivity.sharedPreferences.getStringSet("newsDownloadsFileName", new HashSet<>());
        for (String newsName : newsDownloadsFileName) {
            newsList.add(FileHandler.readNewsFromLocal(this, newsName));
        }

        if (!newsList.isEmpty()) {
            for (News news : newsList) {
                // acquire news detail
                View view = inflater.inflate(R.layout.news_item, itemsCollectionLayout, false);
                itemsCollectionLayout.addView(view);
                TextView titleTextView = view.findViewById(R.id.news_item_title);
                titleTextView.setText(news.title);
                ImageView imageView = view.findViewById(R.id.news_item_img);
                imageView.setImageResource(news.imgLocation);
                TextView dateTextView = view.findViewById(R.id.news_item_date);
                dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(news.postDate));
                view.setOnClickListener(v -> {
                    Intent intent = new Intent(this, DetailDownloadActivity.class);
                    intent.putExtra("news", news);
                    startActivity(intent);
                });
            }
        } else {
            View view = inflater.inflate(R.layout.nothing_tips, itemsCollectionLayout, false);
            itemsCollectionLayout.addView(view);
        }
    }

    private void rebuildItemsLayout() {
        LinearLayout linearLayout = findViewById(R.id.news_download_items_layout);
        linearLayout.removeAllViewsInLayout();
    }
}