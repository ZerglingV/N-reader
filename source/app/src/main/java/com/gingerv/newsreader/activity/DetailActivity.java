package com.gingerv.newsreader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gingerv.newsreader.R;
import com.gingerv.newsreader.database.News;
import com.gingerv.newsreader.file.FileHandler;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // back button
        ImageButton buttonBack = findViewById(R.id.button_detail_back);
        buttonBack.setOnClickListener(v -> this.finish());

        News news = MainActivity.newsDao.findById(getIntent().getIntExtra("newsId", 0));
        TextView titleTextView = findViewById(R.id.news_detail_title);
        titleTextView.setText(news.title);
        ImageView imageView = findViewById(R.id.news_detail_img);
        imageView.setImageResource(news.imgLocation);
        TextView dateTextView = findViewById(R.id.news_detail_date);
        dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(news.postDate));
        TextView contentTextView = findViewById(R.id.news_detail_content);
        contentTextView.setTextSize(getResources().getInteger(R.integer.MIN_FONT_SIZE) + MainActivity.sharedPreferences.getInt("fontSize", 0) * 2);
        contentTextView.setText(news.content);

        // get news download filename set
        Set<String> newsDownloadsFileName = MainActivity.sharedPreferences.getStringSet("newsDownloadsFileName", new HashSet<>());
        // download button
        ImageButton buttonDownload = findViewById(R.id.button_download);
        buttonDownload.setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.tips_download_start), Toast.LENGTH_SHORT).show();
            if (FileHandler.writeNewsIntoLocal(this, "news_" + news.id, news)) {
                newsDownloadsFileName.add("news_" + news.id);
                // set news download count
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putStringSet("newsDownloadsFileName", newsDownloadsFileName);
                editor.apply();
                Toast.makeText(this, getString(R.string.tips_download_finished), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.tips_download_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}