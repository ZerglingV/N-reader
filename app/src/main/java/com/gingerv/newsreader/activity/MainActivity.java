package com.gingerv.newsreader.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import com.gingerv.newsreader.ImageHandler;
import com.gingerv.newsreader.R;
import com.gingerv.newsreader.broadcast.SmsBroadcastReceiver;
import com.gingerv.newsreader.database.AppDatabase;
import com.gingerv.newsreader.database.NewsDao;
import com.gingerv.newsreader.service.BackgroundService;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    public static NewsDao newsDao;
    public static SharedPreferences sharedPreferences;

    SmsBroadcastReceiver smsReceiver;
    IntentFilter smsFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // read saved settings, must be here
        loadSettings();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start online music
        startBackgroundService();
        // create broadcast receiver
        createSmsBroadcastReceiver();
        // create database
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "app_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        newsDao = appDatabase.newsDao();
        // music imageView rotation handle
        setMusicRotation();
        // category button handle
        setCategoryButton();
        // menu items handle
        setMenuItems();
    }

    private void loadSettings() {
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // language setting
        if (sharedPreferences.getInt("language", R.id.radioButton_chinese) == R.id.radioButton_chinese) {
            setLanguage(Locale.SIMPLIFIED_CHINESE);
        } else {
            setLanguage(Locale.ENGLISH);
        }
        if (sharedPreferences.getBoolean("darkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setLanguage(Locale locale) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void startBackgroundService() {
        Intent backgroundService = new Intent(this, BackgroundService.class);
        this.startService(backgroundService);
    }

    private void createSmsBroadcastReceiver() {
        smsReceiver = new SmsBroadcastReceiver();
        smsFilter = new IntentFilter();
        smsFilter.addAction(getString(R.string.SMS_RECEIVED));
        registerReceiver(smsReceiver, smsFilter);
    }

    private void setMenuItems() {
        // open menu when pressing menu button
        DrawerLayout drawerLayout = findViewById(R.id.main_layout);
        ImageButton buttonMenu = findViewById(R.id.button_menu);
        buttonMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        // items handle
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    drawerLayout.closeDrawers();
                    break;
                case R.id.menu_downloads:
                    Intent intent = new Intent(this, DownloadsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;
                case R.id.menu_about:
                    Uri uri = Uri.parse(getString(R.string.APP_ABOUT_ADDRESS));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(browserIntent);
                    break;
                case R.id.menu_share:
                    // copy to clipboard
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newRawUri("Label", Uri.parse(getString(R.string.APP_DOWNLOAD_ADDRESS)));
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(this, getString(R.string.tips_copy_download_link), Toast.LENGTH_SHORT).show();
                    // share with sms
                    Intent smsIntent = new Intent();
                    smsIntent.setAction(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse("smsto:"));
                    smsIntent.putExtra("sms_body", getString(R.string.APP_DOWNLOAD_ADDRESS));
                    startActivity(smsIntent);
                    break;
                case R.id.menu_database_add_items:
                    try {
                        appDatabase.addTestNewsItems(this);
                        Toast.makeText(this, getString(R.string.tips_database_add_info), Toast.LENGTH_SHORT).show();
                    } catch (Exception exception) {
                        Toast.makeText(this, getString(R.string.tips_database_add_warning), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.menu_database_delete_items:
                    appDatabase.deleteTestNewsItems();
                    Toast.makeText(this, getString(R.string.tips_database_delete_info), Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void setMusicRotation() {
        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.music_rotation);
        ImageView music_image = findViewById(R.id.music_image);
        LinearInterpolator interpolator = new LinearInterpolator();  // Set constant rotation, in the XML file set will appear stalled
        circle_anim.setInterpolator(interpolator);
        music_image.startAnimation(circle_anim);
    }

    private void setCategoryButton() {
        // image handle
        ImageHandler imageHandler = new ImageHandler(this);
        imageHandler.NewsImageRoundedCorner();

        // set entrance activity_in_category
        ImageButton buttonNewsAutomobile = findViewById(R.id.button_news_automobile);
        buttonNewsAutomobile.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.AUTOMOBILE));
            startActivity(intent);
        });

        ImageButton buttonNewsFashion = findViewById(R.id.button_news_fashion);
        buttonNewsFashion.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.FASHION));
            startActivity(intent);
        });

        ImageButton buttonNewsFinEco = findViewById(R.id.button_news_fin_eco);
        buttonNewsFinEco.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.FIN_ECO));
            startActivity(intent);
        });

        ImageButton buttonNewsFood = findViewById(R.id.button_news_food);
        buttonNewsFood.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.FOOD));
            startActivity(intent);
        });

        ImageButton buttonNewsGame = findViewById(R.id.button_news_game);
        buttonNewsGame.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.GAME));
            startActivity(intent);
        });

        ImageButton buttonNewsHealth = findViewById(R.id.button_news_health);
        buttonNewsHealth.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.HEALTH));
            startActivity(intent);
        });

        ImageButton buttonNewsSports = findViewById(R.id.button_news_sports);
        buttonNewsSports.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.SPORTS));
            startActivity(intent);
        });

        ImageButton buttonNewsTech = findViewById(R.id.button_news_tech);
        buttonNewsTech.setOnClickListener(v -> {
            Intent intent = new Intent(this, InCategoryActivity.class);
            intent.putExtra("category", getResources().getInteger(R.integer.TECHNOLOGY));
            startActivity(intent);
        });
    }
}