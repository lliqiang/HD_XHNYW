package com.hengda.smart.xhnyw.d.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hengda.frame.hdplayer.BasePlayerActivity;
import com.hengda.frame.hdplayer.HDExoPlayer;
import com.hengda.frame.hdplayer.MusicService;
import com.hengda.frame.hdplayer.model.MusicTrack;
import com.hengda.frame.hdplayer.util.ControllerUtil;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.CheckResponse;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.hengda.smart.xhnyw.d.model.HttpResponse;
import com.hengda.smart.xhnyw.d.model.ResUpdate;
import com.hengda.smart.xhnyw.d.ui.my.PartnerActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hengda.frame.hdplayer.util.ControllerUtil.stringForTime;

/**
 * @author 李强.
 * @explain 播放界面
 * @time 2017/6/23 14:00.
 */
public class PlayActivity extends BasePlayerActivity implements View.OnClickListener {

    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.los_common)
    ImageView losCommon;
    @Bind(R.id.iv_play_show)
    ImageView ivPlayShow;
    @Bind(R.id.iv_play_player)
    ImageView ivPlayPlayer;
    @Bind(R.id.tv_totalTime)
    TextView tvTotalTime;
    @Bind(R.id.tv_startTime)
    TextView tvStartTime;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.wv_play)
    WebView wvPlay;
    private boolean dragging;
    private PlaybackStatus mPlaybackStatus;
    private ExhibitInfo exhibitionBean;
    private String path;
    private String wevPath;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        exhibitionBean = (ExhibitInfo) getIntent().getExtras().getSerializable("ExhibitionBean");
        losCommon.setVisibility(View.VISIBLE);

        if (Hd_AppConfig.getLanguage().equals("CHINESE")){
            if (!TextUtils.isEmpty(exhibitionBean.getName())) {
                tvTilteCommon.setText(exhibitionBean.getName());
            }
        }else {
            if (!TextUtils.isEmpty(exhibitionBean.getEn_name())) {
                tvTilteCommon.setText(exhibitionBean.getEn_name());
            }
        }

        initController();
        initPlaybackStatus();
        initWeb();
        initPlay();
        initListner();
        initScan();
    }

    private void initScan() {
        EasyHttp.post("index.php?g=mapi&m=Resource&a=exhibit_look")
                .params("exhibit_id", exhibitionBean.getExhibit_id())
                .params("deviceno", Hd_AppConfig.getDeviceNo())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String response) {
                        HttpResponse httpResponse = new Gson().fromJson(response, HttpResponse.class);
                    }
                });
    }

    private void initListner() {
        losCommon.setOnClickListener(this);
        imgBackCommon.setOnClickListener(this);
    }

    private void initWeb() {
        WebSettings settings = wvPlay.getSettings();
        wvPlay.setBackgroundColor(0);
        wvPlay.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wevPath = Hd_AppConfig.getExhibitResPath(exhibitionBean.getExhibit_id()) + ".html";
        imgPath = Hd_AppConfig.getDefaultFileDir() + "exhibit/" + exhibitionBean.getExhibit_id() + "/" + "image/" + exhibitionBean.getExhibit_id() + ".png";
        Glide.with(PlayActivity.this).load(imgPath).into(ivPlayShow);
        wvPlay.loadUrl("file:///" + wevPath);
    }

    private void initPlay() {
        path = Hd_AppConfig.getExhibitResPath(exhibitionBean.getExhibit_id()) + ".mp3";
        if (HDExoPlayer.isPlaying()) {
            ivPlayPlayer.setImageResource(R.mipmap.img_pause);
        } else if (!HDExoPlayer.isPlaying() && HDExoPlayer.getPosition() == 0) { // begin play
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Hd_AppConfig.getLanguage().equals("CHINESE")) {
                        HDExoPlayer.prepare(new MusicTrack(Integer.parseInt(exhibitionBean.getAutonum()), exhibitionBean.getName(), path, R.drawable.def_placeholder, R.drawable.def_placeholder), true);
                    } else {
                        HDExoPlayer.prepare(new MusicTrack(Integer.parseInt(exhibitionBean.getAutonum()), exhibitionBean.getEn_name(), path, R.drawable.def_placeholder, R.drawable.def_placeholder), true);
                    }
                }
            }, 500);

            ivPlayPlayer.setImageResource(R.mipmap.img_pause);
        } else if (!HDExoPlayer.isPlaying() && HDExoPlayer.getPosition() != 0) { //pausing
            ivPlayPlayer.setImageResource(R.mipmap.img_play);
        }
    }

    private void initController() {
        seekBar.setMax(ControllerUtil.PROGRESS_BAR_MAX);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvStartTime.setText(stringForTime(progress * HDExoPlayer.getDuration() / ControllerUtil.PROGRESS_BAR_MAX));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                dragging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dragging = false;
                HDExoPlayer.seekTo(seekBar.getProgress() * HDExoPlayer.getDuration() / ControllerUtil.PROGRESS_BAR_MAX);
            }
        });
        ivPlayPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!HDExoPlayer.isPlaying()) {
                    ivPlayPlayer.setImageResource(R.mipmap.img_pause);
                    HDExoPlayer.play();
                } else {
                    ivPlayPlayer.setImageResource(R.mipmap.img_play);
                    HDExoPlayer.pause();
                }
            }
        });
    }

    private void initPlaybackStatus() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.POSITION_CHANGED);
        filter.addAction(MusicService.STATE_BUFFERING);
        filter.addAction(MusicService.STATE_ERROR);
        filter.addAction(MusicService.STATE_COMPLETED);

        mPlaybackStatus = new PlaybackStatus();
        registerReceiver(mPlaybackStatus, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPlaybackStatus);
        HDExoPlayer.seekTo(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.los_common:
                HDExoPlayer.pause();
                ivPlayPlayer.setImageResource(R.mipmap.img_play);
                Intent intent = new Intent(PlayActivity.this, DeviceActivity.class);
                intent.putExtra("ExhibitionBean", exhibitionBean);
                startActivity(intent);
                break;
            case R.id.img_back_common:
                finish();
                break;
        }

    }

    private final class PlaybackStatus extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (action.equals(MusicService.POSITION_CHANGED)) {
                long duration = intent.getExtras().getLong("duration");
                long position = intent.getExtras().getLong("position");
                long bufferedPosition = intent.getExtras().getLong("bufferedPosition");
                if (!dragging) {
                    seekBar.setProgress(ControllerUtil.progressBarValue(duration, position));
                    seekBar.setSecondaryProgress(ControllerUtil.progressBarValue(duration, bufferedPosition));
                    tvStartTime.setText(ControllerUtil.stringForTime(position));
                    tvTotalTime.setText("/" + ControllerUtil.stringForTime(duration));
                }
            } else if (action.equals(MusicService.STATE_BUFFERING)) {

            } else if (action.equals(MusicService.STATE_ERROR)) {

            } else if (action.equals(MusicService.STATE_COMPLETED)) {
                HDExoPlayer.seekTo(0);
                ivPlayPlayer.setImageResource(R.mipmap.img_play);

            }
        }
    }

    public ExhibitInfo getFloorInfo(int autoNum) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        Cursor cursor = HResDdUtil.getInstance().loadExhibitByAutoNo(autoNum);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                exhibitInfo = ExhibitInfo.CursorToModel(cursor);
            }
            cursor.close();

        }
        return exhibitInfo;
    }

    public static void open(Context context, ExhibitInfo bean) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("ExhibitionBean", bean);
        context.startActivity(intent);
    }

}
