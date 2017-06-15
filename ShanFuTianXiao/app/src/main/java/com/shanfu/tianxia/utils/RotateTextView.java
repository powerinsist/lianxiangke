package com.shanfu.tianxia.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**  
 * 版权：  极智网络技术
 * @author lanhm  
 * @version 创建时间：2013-7-2 下午12:56:12  
 * 类说明  
 */

@SuppressLint("AppCompatCustomView")
public class RotateTextView extends TextView{

    
    public RotateTextView(Context context) {
        super(context);
    }
    
    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
	
}
