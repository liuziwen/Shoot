package com.example.lzw.shoot.Myset;



import android.app.Activity;
;
import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lzw.shoot.R;


public class SetListFragment extends ListFragment {
   private Callbacks mCallbacks;
    public interface Callbacks{public void onItemSelected(int id);};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] str={"对战模式","问答题难度","AI玩家级别","背景音乐","音量","音效"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),R.layout.setlistlayout,R.id.setlisttextview,str));
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_set_list, container, false);
//    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!(activity instanceof Callbacks)){}
        mCallbacks=(Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void onListItemClick(ListView lv,View v,int position,long id){
        super.onListItemClick(lv,v,position,id);
        mCallbacks.onItemSelected(position);

    }
    public void setActivateOnItemClick(boolean b){
        getListView().setChoiceMode(b?ListView.CHOICE_MODE_SINGLE:ListView.CHOICE_MODE_NONE);
    }

}
