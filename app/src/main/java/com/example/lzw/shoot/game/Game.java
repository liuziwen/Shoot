package com.example.lzw.shoot.game;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lzw.shoot.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Game extends Activity {
    static SharedPreferences mySharedPreferences;
    static SharedPreferences.Editor editor;
    Button sure;
    TextView question,quesnum,difficulty,customspass;
    RadioButton answer1,answer2,answer3,answer4;
    RadioGroup rg;
    ImageView arrow1,arrow2,arrow3;
    int answer,myanswer,number=1,rightanswer=0;
    String jiexi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sure=(Button)findViewById(R.id.sure);
        rg=(RadioGroup)findViewById(R.id.rg);
        question=(TextView)findViewById(R.id.question);
        quesnum=(TextView)findViewById(R.id.quesnum);
        difficulty=(TextView)findViewById(R.id.difficulty);
        customspass=(TextView)findViewById(R.id.customspass);
        answer1=(RadioButton)findViewById(R.id.answer1);
        answer2=(RadioButton)findViewById(R.id.answer2);
        answer3=(RadioButton)findViewById(R.id.answer3);
        answer4=(RadioButton)findViewById(R.id.answer4);
        arrow1=(ImageView)findViewById(R.id.arrow1);
        arrow2=(ImageView)findViewById(R.id.arrow2);
        arrow3=(ImageView)findViewById(R.id.arrow3);

        mySharedPreferences= getSharedPreferences("mysp", MODE_WORLD_WRITEABLE);

        editor = mySharedPreferences.edit();

        difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("difficulty", 1);
                editor.putInt("customspass", 1);
                editor.commit();
            }
        });
//        editor.putInt("difficulty", 1);
//        editor.putInt("customspass", 1);

        //editor.commit();
        //int n=number+1;
        quesnum.setText("第"+number+"题");
        switch (mySharedPreferences.getInt("difficulty",1)) {
            case 1:
                difficulty.setText("初级1");break;
            case 2:
                difficulty.setText("初级2");break;
            case 3:
                difficulty.setText("中级1");break;
            case 4:
                difficulty.setText("中级2");break;
            case 5:
                difficulty.setText("高级1");break;
            case 6:
                difficulty.setText("高级2");break;
        }
        customspass.setText("关卡"+mySharedPreferences.getInt("customspass",1));
        setQuestion(mySharedPreferences.getInt("difficulty",1),mySharedPreferences.getInt("customspass",1),number-1);


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setChecked(false);
                answer2.setChecked(false);
                answer3.setChecked(false);
                answer4.setChecked(false);
                if(answer==myanswer){
                    rightanswer++;
                    Toast.makeText(Game.this,"回答正确！",Toast.LENGTH_SHORT).show();

                    myanswer=0;
                    showArrows(rightanswer);


                    if(number>=3){
                        Intent in=new Intent(Game.this,PlayGame.class);
                        in.putExtra("number",rightanswer);
                        startActivity(in);
                    }
                    if(number<3)setQuestion(mySharedPreferences.getInt("difficulty",1),mySharedPreferences.getInt("customspass",1),number++);
                    quesnum.setText("第"+number+"题");
                    customspass.setText("关卡"+mySharedPreferences.getInt("customspass",1));
                }


                else {
                    Toast.makeText(Game.this,"回答错误！",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ab=new AlertDialog.Builder(new ContextThemeWrapper(Game.this, R.style.mDialog))
                            .setTitle("解析")
                            .setMessage(jiexi)
                            .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    ab.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (number >= 3) {
                                Intent in = new Intent(Game.this, PlayGame.class);
                                in.putExtra("number",rightanswer);
                                startActivity(in);
                            }
                            if (number < 3)
                                setQuestion(mySharedPreferences.getInt("difficulty", 1), mySharedPreferences.getInt("customspass", 1), number++);
                            quesnum.setText("第" + number + "题");
                            customspass.setText("关卡" + mySharedPreferences.getInt("customspass", 1));
                        }
                    });

                    ab.create().show();

                    }
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.answer1:myanswer=1;answer1.setChecked(true);break;
                    case R.id.answer2:myanswer=2;answer2.setChecked(true);break;
                    case R.id.answer3:myanswer=3;answer3.setChecked(true);break;
                    case R.id.answer4:myanswer=4;answer4.setChecked(true);break;

                }
            }
        });

    }

    public void setQuestion(int difficulty,int customspass ,int number){
        InputStream is = null;
        //FileReader fr=new FileReader(file);
        String file=""+difficulty+customspass;
//        switch(Integer.parseInt(file)){
//            case 11:is = getResources().openRawResource(R.raw.q11);break;
//            case 12:is = getResources().openRawResource(R.raw.q12);break;
//            case 13:is = getResources().openRawResource(R.raw.q13);break;
//            case 14:is = getResources().openRawResource(R.raw.q14);break;
//            case 15:is = getResources().openRawResource(R.raw.q15);break;
//            case 16:is = getResources().openRawResource(R.raw.q26);break;
//            case 21:is = getResources().openRawResource(R.raw.q21);break;
//            case 22:is = getResources().openRawResource(R.raw.q22);break;
//            case 23:is = getResources().openRawResource(R.raw.q23);break;
//            case 24:is = getResources().openRawResource(R.raw.q24);break;
//            case 25:is = getResources().openRawResource(R.raw.q25);break;
//            case 26:is = getResources().openRawResource(R.raw.q26);break;
//            case 31:is = getResources().openRawResource(R.raw.q31);break;
//            case 32:is = getResources().openRawResource(R.raw.q32);break;
//            case 33:is = getResources().openRawResource(R.raw.q33);break;
//            case 34:is = getResources().openRawResource(R.raw.q34);break;
//            case 35:is = getResources().openRawResource(R.raw.q35);break;
//            case 36:is = getResources().openRawResource(R.raw.q36);break;
//            case 41:is = getResources().openRawResource(R.raw.q41);break;
//            case 42:is = getResources().openRawResource(R.raw.q42);break;
//            case 43:is = getResources().openRawResource(R.raw.q43);break;
//            case 44:is = getResources().openRawResource(R.raw.q44);break;
//            case 45:is = getResources().openRawResource(R.raw.q45);break;
//            case 46:is = getResources().openRawResource(R.raw.q46);break;
//            case 51:is = getResources().openRawResource(R.raw.q51);break;
//            case 52:is = getResources().openRawResource(R.raw.q52);break;
//            case 53:is = getResources().openRawResource(R.raw.q53);break;
//            case 54:is = getResources().openRawResource(R.raw.q54);break;
//            case 55:is = getResources().openRawResource(R.raw.q55);break;
//            case 56:is = getResources().openRawResource(R.raw.q56);break;
//            case 61:is = getResources().openRawResource(R.raw.q61);break;
//            case 62:is = getResources().openRawResource(R.raw.q62);break;
//            case 63:is = getResources().openRawResource(R.raw.q63);break;
//            case 64:is = getResources().openRawResource(R.raw.q64);break;
//            case 65:is = getResources().openRawResource(R.raw.q65);break;
//            case 66:is = getResources().openRawResource(R.raw.q66);break;
//        }

       // is = getResources().openRawResource(R.raw.myquestion);
        try {
            is = getAssets().open("q"+difficulty+customspass+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = null;

        reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        String temp=null;  String s="";
        try {
            for(int i=0;i<7*number;)if((temp=br.readLine())!=null&&temp.equals(""))i++;
           // int i=0;
      //      while(i<6){
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                    s+=temp+"\n";
                }
            System.out.println(0+s);
            question.setText(s);
            s="";
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }answer1.setText(s);System.out.println(1+s);
            s="";
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }answer2.setText(s);System.out.println(2+s);
            s="";
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }
            answer3.setText(s);System.out.println(3+s);
            s="";
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }
            answer4.setText(s);System.out.println(4+s);
            s="";
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }
            System.out.println(difficulty+"...........");
            answer=Integer.parseInt(s);System.out.println(5+s);
            for(;(temp=br.readLine())!=null&&!temp.equals("");){
                s+=temp;
            }
            jiexi=s;System.out.println(6+s);
            //jiexi.setText(s);

   //         }
//            while((temp=br.readLine())!=null){
//                s+=temp+"\n";
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String [] ss=s.split("\n");
//        question.setText(ss[0]);
//        answer1.setText(ss[1]);
//        answer2.setText(ss[2]);
//        answer3.setText(ss[3]);
//        answer4.setText(ss[4]);
//        answer=Integer.parseInt(ss[5]);

    }


    public void onResume(){
        super.onResume();
        answer=0;myanswer=0;number=1;rightanswer=0;
        showArrows(0);
        quesnum.setText("第"+number+"题");
        switch (mySharedPreferences.getInt("difficulty",1)) {
            case 1:
                difficulty.setText("初级1");break;
            case 2:
                difficulty.setText("初级2");break;
            case 3:
                difficulty.setText("中级1");break;
            case 4:
                difficulty.setText("中级2");break;
            case 5:
                difficulty.setText("高级1");break;
            case 6:
                difficulty.setText("高级2");break;
        }
        customspass.setText("关卡"+mySharedPreferences.getInt("customspass",1));
        setQuestion(mySharedPreferences.getInt("difficulty",1),mySharedPreferences.getInt("customspass",1),number-1);
    }

    public void showArrows(int n){
        switch(n){
            case 0:arrow1.setVisibility(View.GONE);arrow2.setVisibility(View.GONE);arrow3.setVisibility(View.GONE);break;
            case 1:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.GONE);arrow3.setVisibility(View.GONE);break;
            case 2:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.VISIBLE);arrow3.setVisibility(View.GONE);break;
            case 3:arrow1.setVisibility(View.VISIBLE);arrow2.setVisibility(View.VISIBLE);arrow3.setVisibility(View.VISIBLE);break;
        }
    }



    @Override
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
