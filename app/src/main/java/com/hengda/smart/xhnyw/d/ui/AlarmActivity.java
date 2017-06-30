package com.hengda.smart.xhnyw.d.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AlarmActivity extends BaseActivity {

    AudioManager mAudioManager;
    MediaPlayer mMediaPlayer;
    public static AlarmActivity mInstance = null;
    @Bind(R.id.imgClose)
    ImageView mImgClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mInstance = AlarmActivity.this;
        initAudioManager();
        initPlayer();
        mImgClose.setOnClickListener(v -> finish());
    }

    protected void onDestroy() {
        super.onDestroy();
        mInstance = null;
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initAudioManager() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    public void initPlayer() {
        switch (Hd_AppConfig.getLanguage()) {
            case "CHINESE":
                mMediaPlayer = MediaPlayer.create(this, R.raw.chinese);
                break;
            case "ENGLISH":
                mMediaPlayer = MediaPlayer.create(this, R.raw.english);
                break;

            default:
                mMediaPlayer = MediaPlayer.create(this, R.raw.chinese);
                break;
        }
        mMediaPlayer.setLooping(true);

        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
