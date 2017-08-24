package com.example.pver.mylib.zxing;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pver.mylib.R;
import com.example.pver.mylib.zxing.camera.CameraManager;
import com.example.pver.mylib.zxing.decoding.CaptureActivityHandler;
import com.example.pver.mylib.zxing.decoding.InactivityTimer;
import com.example.pver.mylib.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mylibrary.AfinalActivity;
import com.mylibrary.utils.DialogUtils;
import com.mylibrary.utils.ToastUtil;
import com.mylibrary.widget.bar.BaseTitleBar;
import com.mylibrary.widget.dialog.TipClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 扫一扫 界面
 * Created by zhumg on 4/25.
 */
public class ScanActivity extends AfinalActivity implements SurfaceHolder.Callback {

    private BaseTitleBar baseTitleBar;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private static ScanCallback scanCallback;
    Dialog wheelViewDialog;
    @Override
    public int getContentViewId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView(View view) {
        setTranslucentStatus();
        baseTitleBar = new BaseTitleBar(view);
        baseTitleBar.setLeftBack(this);
        baseTitleBar.setCenterTxt("扫一扫");

        baseTitleBar.setRightTxt("手输", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelViewDialog=createWheelViewDialog();
                wheelViewDialog.show();
            }
        });

        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        getPersimmions();
    }
    public Dialog createWheelViewDialog() {
        final Dialog dialog = new Dialog(this, R.style.Dialog);
        try {

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = View.inflate(ScanActivity.this, R.layout.encode_dialog,
                    null);
            TextView tv_dialog_close = (TextView) view.findViewById(R.id.tv_dialog_close);
            TextView tv_dialog_ok = (TextView) view.findViewById(R.id.tv_dialog_ok);
           final EditText edit_encode = (EditText) view.findViewById(R.id.edit_encode);

            tv_dialog_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation ani = AnimationUtils.loadAnimation(ScanActivity.this, R.anim.alpha_action);
                    v.startAnimation(ani);
                    wheelViewDialog.dismiss();

                }
            });
            tv_dialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation ani = AnimationUtils.loadAnimation(ScanActivity.this, R.anim.alpha_action);
                    v.startAnimation(ani);
                    wheelViewDialog.dismiss();
                    if(edit_encode.getText().toString().trim().length()==0){
                        return;
                    }
                    //TODO 手动输入的订单号
                    String resultString ="supplychain/sequenceNumber="+ edit_encode.getText().toString().trim();
                    if (scanCallback != null) {
                        if(scanCallback.isFinishScanActivity()) {
                            finish();
                        }
                        scanCallback.onScanSuccess(resultString);
                    }else {
                        ToastUtil.showToast(ScanActivity.this, resultString);
                    }

                }
            });
            dialog.setContentView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }
    public static void setScanCallback(ScanCallback callback) {
        scanCallback = callback;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    public static abstract class ScanCallback {
        public boolean isFinishScanActivity() {
            return true;
        }
        public abstract void onScanSuccess(String code);
    }

    /**
     * 扫码回调接口
     *
     * @param result
     */
    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (scanCallback != null) {
            if(scanCallback.isFinishScanActivity()) {
                finish();
            }
            scanCallback.onScanSuccess(resultString);
        }else {
            ToastUtil.showToast(this, resultString);
        }
    }



    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    //    6.0系统以后部分权限要自动获取
    @TargetApi(23)
    protected void getPersimmions() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissions = new ArrayList<String>();
            // 照相机权限
            if (addPermission(permissions, Manifest.permission.CAMERA)) {
                permissionInfo += "Manifest.permission.CAMERA Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }


    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (permissions[0].equals(Manifest.permission.CAMERA)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            DialogUtils.createTipDialog(this, "该功能需要相机权限,请您手动为本程序授权", new TipClickListener() {
                @Override
                public void onClick(boolean left) {
                    if (!left) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
            }).show();
        }
    }
}
