package com.shanfu.tianxia.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.camera.BitmapUtils;
import com.shanfu.tianxia.cameradecoding.CaptureActivityHandler;
import com.shanfu.tianxia.cameradecoding.InactivityTimer;
import com.shanfu.tianxia.cameratwo.CameraManager;
import com.shanfu.tianxia.cameratwoview.ViewfinderView;
import com.shanfu.tianxia.decode.BitmapDecoder;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Vector;

//activity_capture_two
public class CaptureTwoActivity extends BaseFragmentActivity  implements Callback
{

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;//surface有没有被绘制
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;//完成扫描时是否震动提示
        private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_SUC = 200;
    private static final int PARSE_BARCODE_FAIL = 300;
    private ImageView capture_flashlight;
    private LinearLayout capture_scan_photo;
    public static final String SCAN_QRCODE_RESULT = "qrcode_result";//扫码返回结果（字符串）
    public static final String SCAN_QRCODE_BITMAP = "qrcode_bitmap";//扫码结果bitmap

    /**
     * 图片的路径
     */
    private String photoPath;

    private Handler mHandler = new MyHandler(this);
    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }



        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PARSE_BARCODE_SUC: // 解析图片成功
                    Toast.makeText(activityReference.get(),
                           "解析成功，结果为：" + msg.obj, Toast.LENGTH_SHORT).show();

                    break;

                case PARSE_BARCODE_FAIL:// 解析图片失败
                    Toast.makeText(activityReference.get(), "解析图片失败",
                            Toast.LENGTH_SHORT).show();

                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }



    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_two);

        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        capture_flashlight = (ImageView) findViewById(R.id.capture_flashlight);
        capture_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaptureTwoActivity.this, MainActivity.class);
                intent.putExtra("comefrom", "home");
                startActivity(intent);
                finish();
            }
        });
        capture_scan_photo = (LinearLayout) findViewById(R.id.capture_scan_photo);
        capture_scan_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开手机中的相册
                Intent innerIntent = new Intent(Intent.ACTION_PICK); // "android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent,
                        "选择二维码图片");
                startActivityForResult(wrapperIntent, REQUEST_CODE);
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);//activity静止一段时间会自动关闭

    }



    @Override
    protected void onResume()
    {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface)
        {
            initCamera(surfaceHolder);
        }
        else
        {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = false;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
        {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (handler != null)
        {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy()
    {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    //初始化照相机
    private void initCamera(SurfaceHolder surfaceHolder)
    {
        try
        {
            CameraManager.get().openDriver(surfaceHolder);
        }
        catch (IOException ioe)
        {
            return;
        }
        catch (RuntimeException e)
        {
            return;
        }
        if (handler == null)
        {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        if (!hasSurface)
        {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView()
    {
        return viewfinderView;
    }

    public Handler getHandler()
    {
        return handler;
    }

    public void drawViewfinder()
    {
        viewfinderView.drawViewfinder();

    }

    //扫描结果数据
    public void handleDecode(Result obj, Bitmap barcode)
    {
        inactivityTimer.onActivity();
        viewfinderView.drawResultBitmap(barcode);//画结果图片
        playBeepSoundAndVibrate();//启动声音效果
        String str = obj.getText();
        System.out.println("lalalla:" + str);
        Intent it = new Intent(CaptureTwoActivity.this, MainActivity.class);
        it.putExtra("result", str);
        setResult(1, it);
        finish();

    }

    //声音控制
    private void initBeepSound()
    {
        if (playBeep && mediaPlayer == null)
        {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try
            {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            }
            catch (IOException e)
            {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    //启动声音功能
    private void playBeepSoundAndVibrate()
    {
        if (playBeep && mediaPlayer != null)
        {
            mediaPlayer.start();
        }
        if (vibrate)
        {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener()
    {
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            mediaPlayer.seekTo(0);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
               /* if ((source == IntentSource.NONE) && lastResult != null) { // 重新进行扫描
                    restartPreviewAfterDelay(0L);
                    return true;
                }*/
                Intent intent = new Intent(CaptureTwoActivity.this, MainActivity.class);
                intent.putExtra("comefrom", "home");
                startActivity(intent);
                finish();
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent intent) {

        if (resultCode == RESULT_OK) {
            final ProgressDialog progressDialog;
            switch (requestCode) {
                case REQUEST_CODE:

                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(
                            intent.getData(), null, null, null, null);
                    String temp_path = intent.getData().getPath();

                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            int columnIndex = cursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA);
                            photoPath = cursor.getString(columnIndex);
                        }
                        cursor.close();
                        progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("正在扫描...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        new Thread(new Runnable() {

                            @Override
                            public void run() {

                                Bitmap img = BitmapUtils
                                        .getCompressedBitmap(photoPath);

                                BitmapDecoder decoder = new BitmapDecoder(
                                        CaptureTwoActivity.this);
                                Result result = decoder.getRawResult(img);


                                if (result != null) {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_SUC;
                                    String resultStr = ResultParser.parseResult(result)
                                            .toString();
                                  /*  m.obj = resultStr;
                                    setCaptureResult(resultStr);
                                    mHandler.sendMessage(m);*/
                                    Intent it = new Intent(CaptureTwoActivity.this, MainActivity.class);
                                    it.putExtra("result", resultStr);
                                    setResult(1, it);
                                    finish();
                                } else {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_FAIL;
                                    mHandler.sendMessage(m);
                                }

                                progressDialog.dismiss();

                            }
                        }).start();
                        cursor.close();
                    }

                    break;

            }
        }

    }
    private void setCaptureResult(String result){

        Intent data = new Intent();
        data.putExtra(SCAN_QRCODE_RESULT, result);
        setResult(0, data);
        finish();
    }

}
