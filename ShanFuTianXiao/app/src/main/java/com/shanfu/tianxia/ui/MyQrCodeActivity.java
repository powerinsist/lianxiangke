package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyQrCodeActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.content_head_back)
    RelativeLayout content_head_back;
    @Bind(R.id.my_qrcode_title_tv)
    TextView my_qrcode_title_tv;
    @Bind(R.id.my_qrcode_share)
    RelativeLayout my_qrcode_share;
    @Bind(R.id.my_qrcode_webview)
    WebView my_qrcode_webview;

    private String url;



    private UMWeb umWeb;
    private UMImage image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        String id = SPUtils.getInstance().getString("uid","");
        String weburl = "https://api.lianxiangke.cn/Home/Reg/spread/id/";

        url = weburl + id;


        my_qrcode_title_tv.setText("我的二维码");
        WebSettings settings = my_qrcode_webview.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");

        content_head_back.setOnClickListener(this);
        my_qrcode_share.setOnClickListener(this);

        my_qrcode_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        my_qrcode_webview.loadUrl(url);

        image = new UMImage(this, R.mipmap.appshare);
        image.setThumb(image);

        /*//大小压缩
        image.compressStyle = UMImage.CompressStyle.SCALE;
        //质量压缩
        image.compressStyle = UMImage.CompressStyle.QUALITY;
        */

        umWeb = new UMWeb(url);
        umWeb.setTitle("联享客~一款更省钱的支付工具");
        umWeb.setThumb(image);
        umWeb.setDescription("我是消费者，我是创业者！2017我为联享客代言！");


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.my_qrcode_share:
                new ShareAction(MyQrCodeActivity.this).setDisplayList(SHARE_MEDIA.SMS,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.WHATSAPP)
                        .withMedia(image)
                        .withMedia(umWeb)
                        .setCallback(umShareListener)
                        .open();

                break;

        }
    }

    private UMShareListener umShareListener = new UMShareListener(){

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            TUtils.showShort(MyQrCodeActivity.this,"分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            TUtils.showShort(MyQrCodeActivity.this,"分享失败了");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            TUtils.showShort(MyQrCodeActivity.this,"分享取消了");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
