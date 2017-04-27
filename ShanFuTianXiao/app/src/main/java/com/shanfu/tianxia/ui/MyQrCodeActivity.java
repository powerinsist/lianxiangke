package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.net.MalformedURLException;
import java.net.URL;

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
//    @Bind(R.id.back_iv)
//    ImageView back_iv;
    private String url;
    private UMWeb umWeb;
    private UMImage image;
//    private UMImage umImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr_code);
        ButterKnife.bind(this);
        initView();
//        //设置代码生成
//        UMShareConfig config = new UMShareConfig();
//        config.isNeedAuthOnGetUserInfo(false);
//        config.isOpenShareEditActivity(true);

//        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
//        config.setFacebookAuthType(UMShareConfig.AUTH_TYPE_SSO);
//        config.setShareToLinkedInFriendScope(UMShareConfig.LINKED_IN_FRIEND_SCOPE_ANYONE);
//        //授权代码
//        UMShareAPI.get(this).getPlatformInfo(this,SHARE_MEDIA.WEIXIN , (UMAuthListener) umShareListener);
    }

    private void initView(){
        String id = SPUtils.getInstance().getString("uid","");
        String weburl = "https://api.lianxiangke.cn/Home/Reg/show/id/";
//        https://api.lianxiangke.cn/Public/user/10003472.jpg
//        https://api.lianxiangke.cn/Home/Reg/index/id/10003091
//        https://api.lianxiangke.cn/Home/Reg/show/id/10003091
//        url = weburl + id + ".jpg";
        url = weburl + id;
//        Log.e("LOG",url);

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
        umWeb.setTitle("花本该花的钱，得本得不到的利-联享客");
        umWeb.setThumb(image);
        umWeb.setDescription("我是消费者，我是创业者！2017我为联享客代言！");

//        umImage = new UMImage(MyQrCodeActivity.this,url);
//        umImage.setThumb(image);
//        umImage.setTitle("花本该花的钱，得本得不到的利-联享客");
//        umImage.setDescription("我是消费者，我是创业者！2017我为联享客代言！");
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.my_qrcode_share:
                new ShareAction(MyQrCodeActivity.this).setDisplayList(SHARE_MEDIA.SMS,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.WHATSAPP)
//                        .withText("分享")
                        .withMedia(image)
                        .withMedia(umWeb)
//                        .withMedia(umImage)
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
