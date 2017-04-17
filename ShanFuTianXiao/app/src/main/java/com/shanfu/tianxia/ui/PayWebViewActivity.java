package com.shanfu.tianxia.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayWebViewActivity extends BaseFragmentActivity {

    @Bind(R.id.webview)
    WebView webview;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_web_view);
        ButterKnife.bind(this);
       // url ="http://pay.soopay.net/spay/pay/payservice.do?amount=100&amt_type=RMB&charset=UTF-8&goods_id=&mer_cust_id=&mer_date=20170325&mer_id=8597&notify_url=https%3A%2F%2Fapi.lianxingke.cn%2Fpay%2Fh5%2Fnotify.php&order_id=431539&res_format=HTML&ret_url=https%3A%2F%2Fapi.lianxingke.cn%2Fpay%2Fh5%2F&service=pay_req_h5_frontpage&sign=&sign_type=RSA&usr_pay_agreement_id=&version=4.0&sign=ccBeMY1NA4EhbhbsPOWYklP7U6okCdeIbqTkqqn0qOqu%2B2TiO76%2BETYlruohd4fnNeL7ZbshAt99dVb86ffIe8cxqwNg6v8PDbFLhcL0KIYxwj2H%2F9LEum%2BD7vZ%2BK6tNdsBwi1fSvmx3f4NsXKhbKwzUz%2FGucWX4QYLB1aFXSeo%3D";
       //url = "http://pay.soopay.net/spay/pay/payservice.do?amount=1&amt_type=RMB&charset=UTF-8&goods_id=&mer_cust_id=&mer_date=20170326&mer_id=8597&notify_url=https%3A%2F%2Fapi.lianxingke.cn%2Fpay%2Fh5%2Fnotify.php&order_id=532321&res_format=HTML&ret_url=https%3A%2F%2Fapi.lianxingke.cn%2Fpay%2Fh5%2F&service=pay_req_h5_frontpage&sign_type=RSA&usr_pay_agreement_id=&version=4.0&sign=jp1bTBO%2Fw01SWNSmDSTUTqN5YeVC1bpuulp9CAxiNTrO%2Bn8lV%2BPwYFJ3HNmpCd7lLwJr25kKyj1g2E5wDRN18EcvlTIHIUn1eKZoOsSbS8FV6uXGIjD%2B8yPJHMlFGUAnhsVJ3qLlBcjHH7rKYsqdxHMb2XsOkiaOv4qzMIX8d%2FY%3D";
        url = getIntent().getStringExtra("url");
        initView();
    }
    private void initView(){
        WebSettings settings = webview.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl(url);
    }
}
