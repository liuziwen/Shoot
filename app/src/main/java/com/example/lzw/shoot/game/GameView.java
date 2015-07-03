package com.example.lzw.shoot.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.lzw.shoot.R;

/**
 * Created by lzw on 2015/5/9.
 */
public class GameView extends View {

    int arrowWidth;
    int arrowHeight;
    static int targetHeight;
    static int targetWidth;
    static int isGameOver=0;
    Bitmap shooter,target,arrow;

    Paint paint = new Paint();

    public GameView(Context context, AttributeSet attrs) {
        super(context,attrs);
        shooter = BitmapFactory.decodeResource(context.getResources(), R.drawable.shooter);
        shooter = Bitmap.createScaledBitmap(shooter,150,150,true);
        target = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        Matrix matrix=new Matrix();
        matrix.setRotate(90);
        arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow1);
        arrow=Bitmap.createBitmap(arrow,0,0,arrow.getWidth(),arrow.getHeight(),matrix,true);
        matrix.setScale(0.5f,0.5f);
        arrow=Bitmap.createBitmap(arrow,0,0,arrow.getWidth(),arrow.getHeight(),matrix,true);
        arrowHeight=arrow.getHeight();
        arrowWidth=arrow.getWidth();
        targetHeight=target.getHeight();
        targetWidth=target.getWidth();
        setFocusable(true);
    }



    // 重写View的onDraw方法，实现绘画
    public void onDraw(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);
        // 设置去锯齿
        paint.setAntiAlias(true);
        // 如果游戏已经结束
        if (isGameOver>0) {
            paint.setColor(Color.RED);
            paint.setTextSize(40);
            if(isGameOver==2)
            canvas.drawText("失败", 50, 200, paint);
            if(isGameOver==1)
                canvas.drawText("过关", 50, 200, paint);
        }
        // 如果游戏还未结束
        else {
            canvas.drawBitmap(shooter, PlayGame.shooterx, PlayGame.shootery, paint);
            canvas.drawBitmap(target, PlayGame.targetx, PlayGame.targety, paint);
            canvas.drawBitmap(arrow,PlayGame.arrowx, PlayGame.arrowy, paint);
        }
    }
}
