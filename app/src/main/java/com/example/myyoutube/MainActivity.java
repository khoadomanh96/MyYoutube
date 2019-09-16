package com.example.myyoutube;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoControlsMobile;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.squareup.picasso.Picasso;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnPreparedListener {
    private static final String APPLICATION_RAW_PATH =
            "android.resource://com.example.myyoutube/";
    private static final String VIDEO_POSTER =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-rhino.jpg";
    private static final String VIDEO_THUMBNAIL =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-green-goblin.jpg";
    private static final String VIDEO_TITLE = "The Amazing Spider-Man 2: Rise of Electro";

    DraggableView draggableView;
    VideoView videoView;
    ImageView thumbnailImageView;
    ImageView posterImageView;
    private VideoControlsMobile mVideoControlsMoblie;
    private VideoControlsGoMin mVideoControlGoMin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        videoView = findViewById(R.id.video_view);
        draggableView = findViewById(R.id.draggable_view);
        thumbnailImageView = findViewById(R.id.iv_thumbnail);
        posterImageView = findViewById(R.id.iv_poster);
        mVideoControlsMoblie = new VideoControlsMobile(this);
        mVideoControlGoMin = new VideoControlsGoMin(this);
        initializeVideoView();
        initializePoster();
        hookDraggableViewListener();
    }
    /**
     * Method triggered when the iv_thumbnail widget is clicked. This method shows a toast with the
     * video title.
     */
    @OnClick(R.id.iv_thumbnail) void onThubmnailClicked() {
        Toast.makeText(this, VIDEO_TITLE, Toast.LENGTH_SHORT).show();
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
            }

            //Empty
            @Override public void onMinimized() {
                //Empty
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
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    /**
     * Resume the VideoView content.
     */
    private void startVideo() {
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    /**
     * Initialize ViedeoView with a video by default.
     */
    private void initializeVideoView() {
        // Make sure to use the correct VideoView import
        videoView.setOnPreparedListener(this);
        //For now we just picked an arbitrary item to play
        videoView.setVideoURI(Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"));
        videoView.setControls(mVideoControlGoMin);
    }

    /**
     * Initialize some ImageViews with a video poster and a video thumbnail.
     */
    private void initializePoster() {
        Picasso.with(this)
                .load(VIDEO_POSTER)
                .placeholder(R.drawable.spiderman_placeholder)
                .into(posterImageView);
        Picasso.with(this)
                .load(VIDEO_THUMBNAIL)
                .placeholder(R.drawable.spiderman_placeholder)
                .into(thumbnailImageView);
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }
}

