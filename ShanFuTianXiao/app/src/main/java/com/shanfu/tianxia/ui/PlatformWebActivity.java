package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlatformWebActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.platform_webview)
    WebView platform_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_web);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText(title);
        content_head_back.setOnClickListener(this);

        String url = getIntent().getStringExtra("url");
        WebSettings settings = platform_webview.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");

        platform_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        platform_webview.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;

        }
    }
}
