package br.com.appdev.mediaapp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class Player {

    private  static final int NEW = 1;
    private  static final int PLAYING = 2;
    private  static final int PAUSED = 3;
    private  static final int STOPPED = 4;

    private int status = NEW;

    private MediaPlayer mediaPlayer;
    private String audio;
    private Context context;

    public Player(Context context, String audio) {
        this.context = context;
        this.audio = audio;
        mediaPlayer = new MediaPlayer();
        this.init();
    }

    public void init() {
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(audio);
            mediaPlayer.setDataSource(
                    fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        switch (status) {
            case PLAYING:
                mediaPlayer.stop();
            case STOPPED:
                mediaPlayer.reset();
                init();
            case NEW:
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case PAUSED:
                mediaPlayer.start();
        }
        status = PLAYING;
    }

    public void pause() {
        if (status == PLAYING) {
            mediaPlayer.pause();
            status = PAUSED;
        }
    }

    public void stop() {
        if (status == PLAYING || status == PAUSED) {
            mediaPlayer.stop();
            status = STOPPED;
        }
    }

    public void close() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
