package com.gingerv.newsreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.gingerv.newsreader.network.OnlineMusicPlayer;

public class BackgroundService extends Service {
    public OnlineMusicPlayer onlineMusicPlayer;
    private static final String MUSIC_ADDRESS = "http://music.163.com/song/media/outer/url?id=1060914.mp3";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onlineMusicPlayer = new OnlineMusicPlayer();
        new Thread(() -> onlineMusicPlayer.playUrl(MUSIC_ADDRESS)).start();
    }
}
