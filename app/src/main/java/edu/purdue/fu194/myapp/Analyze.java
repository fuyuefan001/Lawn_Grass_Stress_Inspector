package edu.purdue.fu194.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Fuyuefan on 2017/8/8.
 */

public class Analyze extends Activity {

    String picturePath;
    String plantPath;
    String whitePath;

    //Bitmap plantBMP= BitmapFactory.decodeFile(intent.getStringExtra("plantPath"));
    //Bitmap whiteBMP= BitmapFactory.decodeFile(intent.getStringExtra("whitePath"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        picturePath=(String)intent.getSerializableExtra("picturePath");
        plantPath=(String)(intent.getSerializableExtra("plantPath"));
        whitePath=(String)(intent.getSerializableExtra("whitePath"));
    }

    public void analyze() {
        //TODO:xxx
        try {

            FileOutputStream fos=new FileOutputStream(FullscreenActivity.PathText,true);
            fos.write((picturePath+"\t"+plantPath+"\t"+whitePath+"plant state"+"\r\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
