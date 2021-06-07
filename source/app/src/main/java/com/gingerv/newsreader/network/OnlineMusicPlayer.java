package com.gingerv.newsreader.network;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class OnlineMusicPlayer implements MediaPlayer.OnPreparedListener {
    public MediaPlayer mediaPlayer;

    public OnlineMusicPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// set media stream type
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // set data source
            mediaPlayer.prepareAsync(); // prepare auto-play
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
