package com.example.myyoutube;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  {
    private String videoUrl = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8";
    private static final String APPLICATION_RAW_PATH =
            "android.resource://com.example.myyoutube/";
    private static final String VIDEO_POSTER =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-rhino.jpg";
    private static final String VIDEO_THUMBNAIL =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-green-goblin.jpg";
    private static final String VIDEO_TITLE = "The Amazing Spider-Man 2: Rise of Electro";
//    @BindView(R.id.draggable_view)
    DraggableView draggableView;
//    @BindView(R.id.video_view)
    PlayerView videoView;
//    @BindView(R.id.iv_thumbnail)
    ImageView thumbnailImageView;
//    @BindView(R.id.iv_poster)
    ImageView posterImageView;
//    @BindView(R.id.btnDemo)
    Button btnDemo;

    SimpleExoPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        draggableView = findViewById(R.id.draggable_view);
        videoView = findViewById(R.id.video_view);
        thumbnailImageView = findViewById(R.id.iv_thumbnail);
        thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, VIDEO_TITLE, Toast.LENGTH_SHORT).show();
            }
        });
        posterImageView = findViewById(R.id.iv_poster);
        btnDemo = findViewById(R.id.btnDemo);
        posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draggableView.maximize();
            }
        });
        initializePoster();
        hookDraggableViewListener();
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Demo Button was clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        player = ExoPlayerFactory.newSimpleInstance(MainActivity.this, new DefaultTrackSelector());
        videoView.setPlayer(player);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this,
                Util.getUserAgent(MainActivity.this,
                        MainActivity.this.getApplicationInfo().loadLabel(MainActivity.this.getPackageManager()).toString()));

        switch (Util.inferContentType(Uri.parse(videoUrl))) {
            case C.TYPE_HLS:
                MediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
                player.prepare(mediaSource);
                break;
            case C.TYPE_OTHER:
                MediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
                player.prepare(extractorMediaSource);
                break;
            default:
                break;
        }

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                error.printStackTrace();
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true);
    }

    /**
     * Method triggered when the iv_thumbnail widget is clicked. This method shows a toast with the
     * video title.
     */
    @OnClick(R.id.iv_thumbnail) void onThubmnailClicked() {
        Toast.makeText(MainActivity.this, VIDEO_TITLE, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method triggered when the iv_poster widget is clicked. This method maximized the draggableView
     * widget.
     */
    @OnClick(R.id.iv_poster) void onPosterClicked() {
        draggableView.maximize();
    }

    /**
     * Hook DraggableListener to draggableView to pause or resume VideoView.
     */
    private void hookDraggableViewListener() {
        draggableView.setDraggableListener(new DraggableListener() {
            @Override public void onMaximized() {
                startVideo();
                Toast.makeText(MainActivity.this, "onMaximized", Toast.LENGTH_SHORT).show();
            }

            //Empty
            @Override public void onMinimized() {
                Toast.makeText(MainActivity.this, "onMinimized", Toast.LENGTH_SHORT).show();
            }

            @Override public void onClosedToLeft() {
                pauseVideo();
            }

            @Override public void onClosedToRight() {
                pauseVideo();
            }
        });
    }

    /**
     * Pause the VideoView content.
     */
    private void pauseVideo() {
        if (isPlaying()) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    /**
     * Resume the VideoView content.
     */
    private void startVideo() {
        if (!isPlaying()){
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    /**
     * Initialize some ImageViews with a video poster and a video thumbnail.
     */
    private void initializePoster() {
        Picasso.with(this)
                .load(VIDEO_POSTER)
                .placeholder(R.drawable.backgroud_viettel_id_account_info)
                .into(posterImageView);
        Picasso.with(this)
                .load(VIDEO_THUMBNAIL)
                .placeholder(R.drawable.backgroud_viettel_id_account_info)
                .into(thumbnailImageView);
    }

    public boolean isPlaying() {
        if(player != null)
            return player.getPlaybackState() == Player.STATE_READY && player.getPlayWhenReady();
        return false;
    }
}

