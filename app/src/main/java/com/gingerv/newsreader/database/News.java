package com.gingerv.newsreader.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class News implements Serializable {
    @PrimaryKey
    public int id;
    public int category; // automobile -> 1, fashion -> 2, fin&eco -> 3, food -> 4, game -> 5, health -> 6, sports -> 7, technology -> 8
    public Date postDate;
    public String title;
    public String content;
    public int imgLocation;

    public News(int id, int category, Date postDate, String title, String content, int imgLocation) {
        this.id = id;
        this.category = category;
        this.postDate = postDate;
        this.title = title;
        this.content = content;
        this.imgLocation = imgLocation;
    }
}
