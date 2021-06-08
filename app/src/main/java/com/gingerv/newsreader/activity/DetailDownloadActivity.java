package com.gingerv.newsreader.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gingerv.newsreader.R;
import com.gingerv.newsreader.database.News;
import com.gingerv.newsreader.file.FileHandler;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DetailDownloadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_download);

        // back button
        ImageButton buttonBack = findViewById(R.id.button_detail_download_back);
        buttonBack.setOnClickListener(v -> this.finish());

        // set current news
        News news = (News) getIntent().getSerializableExtra("news");
        TextView titleTextView = findViewById(R.id.news_detail_download_title);
        titleTextView.setText(news.title);
        ImageView imageView = findViewById(R.id.news_detail_download_img);
        imageView.setImageResource(news.imgLocation);
        TextView dateTextView = findViewById(R.id.news_detail_download_date);
        dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(news.postDate));
        TextView contentTextView = findViewById(R.id.news_detail_download_content);
        contentTextView.setTextSize(getResources().getInteger(R.integer.MIN_FONT_SIZE) + MainActivity.sharedPreferences.getInt("fontSize", 0) * 2);
        contentTextView.setText(news.content);

        // delete button
        ImageButton buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(v -> {
            if (FileHandler.deleteNewsFromLocal(this, getIntent().getStringExtra("news_" + news.id))) {
                Set<String> newsDownloadsFileName = MainActivity.sharedPreferences.getStringSet("newsDownloadsFileName", new HashSet<>());
                newsDownloadsFileName.remove("news_" + news.id);
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putStringSet("newsDownloadsFileName", newsDownloadsFileName);
                editor.apply();
                Toast.makeText(this, getString(R.string.tips_delete_finished), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.tips_delete_failed), Toast.LENGTH_SHORT).show();
            }
            this.finish();
        });
    }
}