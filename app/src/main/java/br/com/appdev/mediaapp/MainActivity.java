package br.com.appdev.mediaapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private Player player;
    private VideoView videoView;
    private ImageButton btnVideo;
    private boolean videoIsPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new Player(this, "elefante.mid");
        videoView = (VideoView) findViewById(R.id.videoView);
        btnVideo = (ImageButton) findViewById(R.id.btnVideo);
        videoView.setVideoURI(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.raw.video
        ));

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnVideo.setImageResource(android.R.drawable.ic_media_play);
                videoIsPlaying = false;
            }
        });
    }

    public void btnVideoOnClick(View view) {
        if (videoIsPlaying) {
            videoView.pause();
            btnVideo.setImageResource(android.R.drawable.ic_media_play);
        } else {
            videoView.start();
            btnVideo.setImageResource(android.R.drawable.ic_media_pause);
        }
        videoIsPlaying = !videoIsPlaying;
    }

    public void btnPlayOnClick(View view) {
        player.play();
    }

    public void btnPauseOnClick(View view) {
        player.pause();
    }

    public void btnStopOnClick(View view) {
        player.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing())
            player.close();
    }
}
