package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReciveMoneyActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    /**
     * 1.收钱金额初始为隐藏gone
     * 2.点击设置金额-->跳转到设置金额页面-->把金额数值带过来并设为visibale
     */
    @Bind(R.id.wallet_changemoney)
    TextView wallet_changemoney;
    @Bind(R.id.wallet_saveimage)
    TextView wallet_saveimage;
    @Bind(R.id.money_tv)
    TextView money_tv;
    @Bind(R.id.wallet_pay_code)
    ImageView wallet_pay_code;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private Intent intent;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_money);
        ButterKnife.bind(this);
        initView();

    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("二维码收款");
        content_head_back.setOnClickListener(this);

        wallet_changemoney.setOnClickListener(this);
        wallet_saveimage.setOnClickListener(this);

        String uid = SPUtils.getInstance().getString("uid", "");
        String user_id = SPUtils.getInstance().getString("user_id", "");
        String name_user = SPUtils.getInstance().getString("name_user", "");

        String money = getIntent().getStringExtra("money");
        if (money == null){
            money = "0.00";
        }
        float v = Float.parseFloat(money);
        if (v > 0){
            money_tv.setText("￥" + money);
            money_tv.setVisibility(View.VISIBLE);
        }
        String add = "e6ISBEYjorj85pCEDc6v9jh1bypb2Y5wtmLW9neSIG+k2bdha/lksyWXhwC+g9/mvZyL/f9vDYdLOBUm0Gf+JgH/ajR9R7FTcj+E9TvhTqttHHVHKZH23DsQL6bL6yhP9ckaZivfN/yHiVJ9JrtnkwQP+7HPoq/VY2YuOZxm6gg=";
        String url = add + "/id/" + uid + "/user_id/" + user_id + "/name_user/" + name_user + "/money/" + money ;
        Log.i(TAG, "initView: "+url);
        createQRImage(url);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //设置金额
            case R.id.wallet_changemoney:
                intent = new Intent(ReciveMoneyActivity.this,WalletPayActivity.class);
                startActivity(intent);
                finish();
                break;
            //保存图片
            case R.id.wallet_saveimage:
                saveImageToGallery(this,bitmap);
                TUtils.showShort(this,"图片保存成功");
                break;
            //返回
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    public void createQRImage(String url)
    {
        //判断URL合法性
        if (url == null || "".equals(url) || url.length() < 1)
        {
            return;
        }
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //图像数据转换，使用了矩阵转换
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int[] pixels = new int[400 * 400];
        //下面这里按照二维码的算法，逐个生成二维码的图片，
        //两个for循环是图片横列扫描的结果
        for (int y = 0; y < 400; y++)
        {
            for (int x = 0; x < 400; x++)
            {
                if (bitMatrix.get(x, y))
                {
                    pixels[y * 400 + x] = 0xff000000;
                }
                else
                {
                    pixels[y * 400 + x] = 0xffffffff;
                }
            }
        }
        //生成二维码图片的格式，使用ARGB_8888
        bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 400, 0, 0, 400, 400);
        //显示到一个ImageView上面
        wallet_pay_code.setImageBitmap(bitmap);
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "LianXiangKe");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,	Uri.fromFile(new File(file.getPath()))));
    }
}
