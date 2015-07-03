package com.example.lzw.shoot.Myset;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lzw.shoot.R;

public class SetDetailFragment extends Fragment {
public static final String ITEMID="item_id";
int id;
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ITEMID)){
        id=getArguments().getInt("item_id");
            mySharedPreferences= getActivity().getSharedPreferences("mysp", Context.MODE_WORLD_WRITEABLE);

            editor = mySharedPreferences.edit();
    }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_set_detail, container, false);
        RadioGroup difficultySet=(RadioGroup)rootView.findViewById(R.id.difficultySet);
        difficultySet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.easy:editor.putInt("difficulty", 1);
                        editor.putInt("customspass", 1);
                        editor.commit();break;
                    case R.id.middle:editor.putInt("difficulty", 3);
                        editor.putInt("customspass", 1);
                        editor.commit();break;
                    case R.id.hard:editor.putInt("difficulty", 5);
                        editor.putInt("customspass", 1);
                        editor.commit();break;
                }
                Toast.makeText(getActivity(),"设置成功！",Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout ll1;
        switch(id){
            case 0:
                ll1=(LinearLayout)rootView.findViewById(R.id.set1);
                ll1.setVisibility(View.VISIBLE);break;
            case 1:
                ll1=(LinearLayout)rootView.findViewById(R.id.set2);
                ll1.setVisibility(View.VISIBLE);break;
            case 2:
                ll1=(LinearLayout)rootView.findViewById(R.id.set3);
                ll1.setVisibility(View.VISIBLE);break;
            case 3:
                ll1=(LinearLayout)rootView.findViewById(R.id.set4);
                ll1.setVisibility(View.VISIBLE);break;
            case 4:
                ll1=(LinearLayout)rootView.findViewById(R.id.set5);
                ll1.setVisibility(View.VISIBLE);break;
            case 5:
                ll1=(LinearLayout)rootView.findViewById(R.id.set6);
                ll1.setVisibility(View.VISIBLE);
//                Switch s=(Switch)rootView.findViewById(R.id.yinxiao);
//                s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    }
//                });
                break;
            default:;
        }
        // Inflate the layout for this fragment
        return rootView;
    }



}
