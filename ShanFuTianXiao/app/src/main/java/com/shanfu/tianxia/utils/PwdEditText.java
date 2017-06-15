package com.shanfu.tianxia.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Power on 2017/4/5.
 */

/**
 *
 * 自定义密码输入框
 *
 */
@SuppressLint("AppCompatCustomView")
public class PwdEditText extends EditText {

    /**
     * 密码画笔
     */
    private Paint mPwdPaint;

    /**
     * 密码框画笔
     */
    private Paint mRectPaint;

    /**
     * 宽度
     */
    private int mWidth;

    /**
     * 高度
     */
    private int mHeight;

    /**
     * 间隔
     */
    private final int PWD_SPACING = 5;

    /**
     * 密码长度
     */
    private final int PWD_LENGTH = 6;

    /**
     * 密码框
     */
    private Rect mRect;

    /**
     * 密码大小
     */
    private final int PWD_SIZE = 5;

    /**
     * 输入密码的长度
     */
    private int mInputLength;

    /**
     * 输入结束监听
     */
    private OnInputFinishListener mOnInputFinishListener;

    private Context mcontext;

    /**
     * 构造方法
     * @param context
     * @param attrs
     */
    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化密码画笔
        mPwdPaint = new Paint();
        mPwdPaint.setColor(Color.BLACK);
        mPwdPaint.setStyle(Paint.Style.FILL);
        mPwdPaint.setAntiAlias(true);
        //初始化密码框画笔
        mRectPaint = new Paint();
        mRectPaint.setColor(Color.LTGRAY);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setAntiAlias(true);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        //这三行代码非常关键，可以注释看效果
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,mWidth,mHeight,paint);

        //计算每个密码框的宽度
        int rectWidth = (mWidth - PWD_SPACING * (PWD_LENGTH - 1)) / PWD_LENGTH;

        //绘制密码框
        for (int i = 0; i < PWD_LENGTH; i++){
            int left = (rectWidth + PWD_SPACING) * i;
            int top = 2;
            int right = left + rectWidth;
            int bottom = mHeight - top;

            mRect = new Rect(left,top,right,bottom);
            canvas.drawRect(mRect,mRectPaint);
        }
//        Log.v("TAG","66666");

        //绘制密码
        for (int i = 0; i < mInputLength; i++){
            int cx = rectWidth / 2 + (rectWidth + PWD_SPACING) * i;
            int cy = mHeight / 2;
            canvas.drawCircle(cx,cy,PWD_SIZE,mPwdPaint);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mInputLength = text.toString().length();
        invalidate();
        //如果输入密码的长度=密码长度&&密码结束监听!=空
        if (mInputLength == PWD_LENGTH && mOnInputFinishListener != null){
            mOnInputFinishListener.onInputFinish(text.toString());
        }
    }

    public interface OnInputFinishListener{

        /**
         * 密码输入结束监听
         *
         */
        void onInputFinish(String password);
    }

    public void setOnInputFinishListener(OnInputFinishListener onInputFinishListener){
        this.mOnInputFinishListener = onInputFinishListener;
    }
}
