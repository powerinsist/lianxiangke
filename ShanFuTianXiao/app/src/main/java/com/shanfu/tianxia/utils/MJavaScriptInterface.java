package com.shanfu.tianxia.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by xuchenchen on 2017/6/13.
 */

public class MJavaScriptInterface {
    private Context context;

    public MJavaScriptInterface(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public void startFunction() {
        Toast.makeText(context, "js调用java", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        new AlertDialog.Builder(context).setMessage(text).show();
    }
}
