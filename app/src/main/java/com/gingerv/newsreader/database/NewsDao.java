package com.gingerv.newsreader.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insert(News... news);

    @Query("delete from news")
    void deleteAll();

    @Query("SELECT * FROM news WHERE id LIKE :id")
    News findById(int id);

    @Query("SELECT * FROM news WHERE category LIKE :category")
    List<News> findByCategory(int category);
}
