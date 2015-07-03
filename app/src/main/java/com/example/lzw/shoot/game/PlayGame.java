package com.example.lzw.shoot.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.example.lzw.shoot.R;

import java.util.Timer;
import java.util.TimerTask;

public class PlayGame extends Activity {
    int arrowNumber;
    ImageView arrow1,arrow2,arrow3;

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    int width,height;

    float direction=0;
    int speed=50;
     GameView gameView;
    Handler handler=new Handler(){
        public void handleMessage(Message m){
            if(m.what==0x123)  {
                gameView.invalidate();
            }
        }
    };
    //aBoolean控制箭的运动 bBoolean控制能否射箭
    boolean aBoolean=false,bBoolean=true,shooterMoveUP=false,shooterMoveDOWN=false,shooterMoveLEFT=false,shooterMoveRIGHT=false;
    Timer timer=new Timer();
    TimerTask timerTask;

    Timer timer2=new Timer();
    TimerTask timerTask2=new TimerTask() {
        @Override
        public void run() {
                handler.sendEmptyMessage(0x123);
        }
    };


    static int shooterx=0;
    static int shootery=0;
    static int targetx=0;
    static int targety=0;
    static int arrowx=-100;
    static int arrowy=0;

    // SurfaceHolder负责维护SurfaceView上绘制的内容
    private SurfaceHolder holder;
    private Paint paint;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_game);
        arrow1=(ImageView)findViewById(R.id.arrow1);
        arrow2=(ImageView)findViewById(R.id.arrow2);
        arrow3=(ImageView)findViewById(R.id.arrow3);

        Button up=(Button)findViewById(R.id.up);
        Button down=(Button)findViewById(R.id.down);
        Button left=(Button)findViewById(R.id.left);
        Button right=(Button)findViewById(R.id.right);
        mySharedPreferences= getSharedPreferences("mysp",MODE_WORLD_WRITEABLE);
        editor = mySharedPreferences.edit();
        arrowNumber=getIntent().getIntExtra("number",3);

        showArrows(arrowNumber);

        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(shooterMoveUP){if(shootery-10>=0&&shootery<height)shootery-=20;}
                if(shooterMoveDOWN){ if(shootery>=0&&shootery+10<height)shootery+=20;}
                if(shooterMoveLEFT){if(shooterx-10>=0&&shooterx<width)shooterx-=20;}
                if(shooterMoveRIGHT){ if(shooterx>=0&&shooterx+10<width)shooterx+=20;}

                if(aBoolean){

                    arrowx += (float) Math.cos(Math.atan(direction)) * speed;
                    arrowy += (float) Math.sin(Math.atan(direction)) * speed;
                }

                targety +=  (int) (Math.random() * 10 - 5);
                targetx +=  (int) (-Math.random() * 10);
                if(arrowx>width||arrowx<0||arrowy>height||arrowy<0){
                    arrowx=shooterx;arrowy=shootery;
                    aBoolean=false;
                   bBoolean=true;}
                if(shooterx>targetx&&shooterx<targetx+GameView.targetWidth&&shootery>targety&&shootery<targety+GameView.targetHeight) {
                    GameView.isGameOver = 2;

                }

                if(aBoolean)
                if(arrowx>targetx&&arrowx<targetx+GameView.targetWidth&&arrowy>targety&&arrowy<targety+GameView.targetHeight){
                    //target.setVisibility(View.GONE);
                    aBoolean=false;
                    bBoolean = true;
                    GameView.isGameOver=1;

                    if (mySharedPreferences.getInt("customspass", 1) < 6) {
                        editor.putInt("customspass", mySharedPreferences.getInt("customspass", 1) + 1);
                    }
                    else if (mySharedPreferences.getInt("difficulty", 1) < 6) {
                        editor.putInt("customspass", 1);
                        editor.putInt("difficulty", mySharedPreferences.getInt("difficulty", 1) + 1);
                    }
                    editor.commit();
                    System.out.println("关卡"+mySharedPreferences.getInt("customspass", 1));

                }

                    handler.sendEmptyMessage(0x123);


            }
        };

         gameView=(GameView)findViewById(R.id.gameview);
         gameView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent e) {

             switch(e.getAction()){
                 case MotionEvent.ACTION_UP:
                      float endx=e.getX();
                      float endy=e.getY();
                      if(endx!=PlayGame.shooterx)
                      if(bBoolean)if(arrowNumber-->0){
                          arrowx=shooterx;
                          arrowy=shootery;
                      showArrows(arrowNumber);
                      direction=(endy-shootery)/(endx-shooterx);
                      bBoolean=false;
                      aBoolean=true;
                 }break;
             }

            return true;


    }
});


        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        arrowy=shootery=targety=height/2;
        arrowx=-100;
                shooterx=0;targetx=width;
        timer.schedule(timerTask,0,100);
        handler=new Handler(){
            public void handleMessage(Message m){
                if(m.what==0x123)  {
                    gameView.invalidate();
                }
            }
        };

       up.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch (event.getAction()){
                   case MotionEvent.ACTION_DOWN:shooterMoveUP=true;break;
                   case MotionEvent.ACTION_UP:shooterMoveUP=false;break;
               }
               return true;
           }
       });

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:shooterMoveDOWN=true;break;
                    case MotionEvent.ACTION_UP:shooterMoveDOWN=false;break;
                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:shooterMoveLEFT=true;break;
                    case MotionEvent.ACTION_UP:shooterMoveLEFT=false;break;
                }
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:shooterMoveRIGHT=true;break;
                    case MotionEvent.ACTION_UP:shooterMoveRIGHT=false;break;
                }
                return true;
            }
        });


    }

    public void showArrows(int n){
        switch(n){
            case 0:arrow1.setVisibility(View.GONE);arrow2.setVisibility(View.GONE);arrow3.setVisibility(View.GONE);break;
            case 1:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.GONE);arrow3.setVisibility(View.GONE);break;
            case 2:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.VISIBLE);arrow3.setVisibility(View.GONE);break;
            case 3:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.VISIBLE);arrow3.setVisibility(View.VISIBLE);break;
        }
    }

//    public class MyGameView extends View {
//
//
//    }


    public void onResume(){
        super.onResume();
        GameView.isGameOver=0;
        arrowNumber=getIntent().getIntExtra("number",3);
        showArrows(arrowNumber);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
