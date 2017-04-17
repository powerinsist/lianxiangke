package com.shanfu.tianxia.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

import com.shanfu.tianxia.R;

/**
 * 自定义密码输入组件
 * @author Administrator
 *
 */
public class MyPassword extends EditText {

       private int start ;
       private String text ;
      
       private Bitmap bmp ;
       private int maxLength = 6;
	private int length;

       public MyPassword(Context context, AttributeSet attrs) {
             super(context, attrs);
            
             bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.mima);
      }
      

       public Bitmap getBmp() {
             return bmp ;
      }
       /***
       * 设置密码覆盖图片
       * @param bmp
       */
       public void setBmp(Bitmap bmp) {
             this.bmp = bmp;
      }
       public int getMaxLength() {
             return maxLength ;
      }
       /**
       * 设置密码的最大长度
       * @param maxLength
       */
       public void setMaxLength(int maxLength) {
             this.maxLength = maxLength;
      }



       @Override
       protected void onDraw(Canvas canvas) {

             int disWidth = getWidth();
             int size = disWidth/maxLength;
             int disHeight = getHeight();

             int paddingLeft = getPaddingLeft();
             int paddingRight = getPaddingRight();
             int paddingTop = getPaddingTop();
             int paddingBottom = getPaddingBottom();

            RectF rect = new RectF(0, 0, disWidth, disHeight);
            RectF rectIn = new RectF(rect.left +1, rect.top+1, rect.right-1, rect.bottom -1);
            Paint paint = getPaint();
            int oldColor = paint.getColor();
            paint.setColor(Color.parseColor("#a2a2a4"));
            canvas.drawRoundRect(rect, 10, 10, paint);
            paint.setColor(Color. WHITE);
            canvas.drawRoundRect(rectIn, 0, 0, paint);
            paint.setColor(Color.parseColor("#a2a2a4"));
             for (int i = 1; i < maxLength; i++) {
                 /* canvas.drawLine(paddingLeft+(disWidth-paddingLeft-paddingRight)*i/ maxLength,
                              paddingTop,
                              paddingLeft+(disWidth-paddingLeft-paddingRight)*i/ maxLength,
                              disHeight-paddingBottom,
                              paint);*/
            	/* canvas.drawLine(paddingLeft+(disWidth-paddingLeft-paddingRight)*i/ maxLength,
                         0,
                         paddingLeft+(disWidth-paddingLeft-paddingRight)*i/ maxLength,
                         disHeight,
                         paint);*/
            	 canvas.drawLine(size*i,
                         0,
                         size*i,
                         disHeight,
                         paint);
            	 
            }

            paint.setColor(oldColor);
            
           //  int textLeftPading = (int) (((disWidth-paddingLeft-paddingRight)/maxLength -bmp .getWidth())/2);
           int length =(disWidth)/6;
         
             if(start <= 0 && text != null && text.length() > 0){
            	
                  //canvas.drawBitmap( bmp, paddingLeft+(disWidth-paddingLeft-paddingRight)*start /maxLength +textLeftPading, (disHeight-bmp.getHeight())/2, paint);
            	 canvas.drawBitmap( bmp,length/2-bmp.getWidth()/2 , (disHeight-bmp.getHeight())/2, paint);
            } else{
                   for (int i = 0; i <= start; i++) {
                         if(text != null && text.length() > start)
                        	
                             // canvas.drawBitmap( bmp, paddingLeft+(disWidth-paddingLeft-paddingRight)*i/maxLength +textLeftPading,(disHeight-bmp.getHeight())/2, paint);
                        	 canvas.drawBitmap( bmp, length*i+length/2-bmp.getWidth()/2 ,(disHeight-bmp.getHeight())/2, paint);
                  }
            }
             //          super.onDraw(canvas);
      }

       @Override
       protected void onTextChanged(CharSequence text, int start,
                   int lengthBefore, int lengthAfter) {
             super.onTextChanged(text, start, lengthBefore, lengthAfter);
             this.text = text.toString();
             length = text.length();
             //this.start = = maxLength-1?start:start;
           
             this.start = lengthBefore>lengthAfter?--start:start;
    	      invalidate(); 
    
            	
            
           
      }
       
   
}
