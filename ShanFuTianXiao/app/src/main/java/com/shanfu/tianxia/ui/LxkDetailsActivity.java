package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LxkDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.lxk_details_webview)
    WebView lxk_details_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lxk_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("联享客");
        content_head_back.setOnClickListener(this);

        WebSettings settings = lxk_details_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        lxk_details_webview.loadUrl("file:///android_asset/lxk_allow.html");
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
