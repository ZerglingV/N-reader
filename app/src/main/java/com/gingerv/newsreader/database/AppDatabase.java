package com.gingerv.newsreader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gingerv.newsreader.activity.MainActivity;
import com.gingerv.newsreader.R;

import java.util.Calendar;

@Database(entities = {News.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    public void addTestNewsItems(Context context) {
        Calendar calendar = Calendar.getInstance();
        int index = 1;
        addTestNewsItem(calendar, index++, 2021, 6, 1, context.getResources().getInteger(R.integer.AUTOMOBILE), context.getString(R.string.automobile_news1_title), context.getString(R.string.automobile_news1_content), R.drawable.automobile_news1);
        addTestNewsItem(calendar, index++, 2021, 6, 1, context.getResources().getInteger(R.integer.AUTOMOBILE), context.getString(R.string.automobile_news2_title), context.getString(R.string.automobile_news2_content), R.drawable.automobile_news2);
        addTestNewsItem(calendar, index++, 2021, 6, 2, context.getResources().getInteger(R.integer.AUTOMOBILE), context.getString(R.string.automobile_news3_title), context.getString(R.string.automobile_news3_content), R.drawable.automobile_news3);
        addTestNewsItem(calendar, index++, 2021, 6, 3, context.getResources().getInteger(R.integer.AUTOMOBILE), context.getString(R.string.automobile_news4_title), context.getString(R.string.automobile_news4_content), R.drawable.automobile_news4);

        addTestNewsItem(calendar, index++, 2021, 5, 12, context.getResources().getInteger(R.integer.TECHNOLOGY), context.getString(R.string.technology_news1_title), context.getString(R.string.technology_news1_content), R.drawable.technology_news1);
        addTestNewsItem(calendar, index++, 2021, 5, 13, context.getResources().getInteger(R.integer.TECHNOLOGY), context.getString(R.string.technology_news2_title), context.getString(R.string.technology_news2_content), R.drawable.technology_news2);

        addTestNewsItem(calendar, index++, 2021, 4, 14, context.getResources().getInteger(R.integer.SPORTS), context.getString(R.string.sports_news1_title), context.getString(R.string.sports_news1_content), R.drawable.sports_news1);
        addTestNewsItem(calendar, index++, 2021, 4, 15, context.getResources().getInteger(R.integer.SPORTS), context.getString(R.string.sports_news2_title), context.getString(R.string.sports_news2_content), R.drawable.sports_news2);

        addTestNewsItem(calendar, index++, 2021, 2, 16, context.getResources().getInteger(R.integer.HEALTH), context.getString(R.string.health_news1_title), context.getString(R.string.health_news1_content), R.drawable.health_news1);
        addTestNewsItem(calendar, index++, 2021, 2, 17, context.getResources().getInteger(R.integer.HEALTH), context.getString(R.string.health_news2_title), context.getString(R.string.health_news2_content), R.drawable.health_news2);

        addTestNewsItem(calendar, index++, 2021, 3, 18, context.getResources().getInteger(R.integer.FIN_ECO), context.getString(R.string.fin_eco_news1_title), context.getString(R.string.fin_eco_news1_content), R.drawable.fin_eco_news1);
        addTestNewsItem(calendar, index++, 2021, 3, 19, context.getResources().getInteger(R.integer.FIN_ECO), context.getString(R.string.fin_eco_news2_title), context.getString(R.string.fin_eco_news2_content), R.drawable.fin_eco_news2);

        addTestNewsItem(calendar, index++, 2021, 1, 18, context.getResources().getInteger(R.integer.GAME), context.getString(R.string.game_news1_title), context.getString(R.string.game_news1_content), R.drawable.game_news1);
        addTestNewsItem(calendar, index++, 2021, 1, 18, context.getResources().getInteger(R.integer.GAME), context.getString(R.string.game_news2_title), context.getString(R.string.game_news2_content), R.drawable.game_news2);

        addTestNewsItem(calendar, index++, 2021, 4, 22, context.getResources().getInteger(R.integer.FASHION), context.getString(R.string.fashion_news1_title), context.getString(R.string.fashion_news1_content), R.drawable.fashion_news1);
        addTestNewsItem(calendar, index++, 2021, 4, 22, context.getResources().getInteger(R.integer.FASHION), context.getString(R.string.fashion_news2_title), context.getString(R.string.fashion_news2_content), R.drawable.fashion_news2);

        addTestNewsItem(calendar, index++, 2020, 12, 15, context.getResources().getInteger(R.integer.FOOD), context.getString(R.string.food_news1_title), context.getString(R.string.food_news1_content), R.drawable.food_news1);

    }

    private void addTestNewsItem(Calendar calendar, int index, int year, int month, int date, int category, String title, String content, int imageLocation) {
        calendar.set(year, month, date);
        News news = new News(index, category, calendar.getTime(), title, content, imageLocation);
        MainActivity.newsDao.insert(news);
    }

    public void deleteTestNewsItems() {
        MainActivity.newsDao.deleteAll();
    }
}
