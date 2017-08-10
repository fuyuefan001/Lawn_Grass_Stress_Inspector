package edu.purdue.fu194.myapp;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fuyuefan on 2017/8/9.
 */

public class History extends ExpandableListActivity{
    public List<String> dateOfPicture;           //组列表
    public Map<String,Object> info;     //子列表


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置为无标题
        setContentView(R.layout.history);
        getExpandableListView().setBackgroundResource(R.drawable.splash_screen);

        getExpandableListView().setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景
    }
    public  void initializeData(){

    }
    public void readData(){
        BufferedReader in= null;
        dateOfPicture=new ArrayList<>();
        info=new HashMap<>();
        try {
            in = new BufferedReader(new FileReader(FullscreenActivity.PathText));

            String out=null;
            int i=0;
            while(( out= in.readLine())!=null){//使用readLine方法，一次读一行
                dateOfPicture.add(i,out.substring(0,out.indexOf("\t")));
                info.put(dateOfPicture.get(i),"plantStateDescription");
                i++;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
