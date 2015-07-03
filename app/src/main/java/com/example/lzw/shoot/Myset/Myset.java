package com.example.lzw.shoot.Myset;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lzw.shoot.R;

public class Myset extends Activity implements SetListFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myset);
        String[] str={"对战模式","问答题难度","AI玩家级别","背景音乐","音量","音效"};
        ListView lv=(ListView)findViewById(R.id.list);
        ArrayAdapter sa=new ArrayAdapter(this,R.layout.setlistlayout,R.id.setlisttextview,str);
        lv.setAdapter(sa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle arguments=new Bundle();
                arguments.putInt("item_id",position);
                SetDetailFragment fragment =new SetDetailFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction().replace(R.id.detail,fragment).commit();
            }
        });
    }

    public void onItemSelected(int id){
        Bundle arguments=new Bundle();
        arguments.putInt("item_id",id);
        SetDetailFragment fragment =new SetDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.detail,fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myset, menu);
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
