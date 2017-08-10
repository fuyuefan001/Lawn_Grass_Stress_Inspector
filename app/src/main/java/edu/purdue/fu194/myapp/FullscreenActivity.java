package edu.purdue.fu194.myapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FullscreenActivity extends AppCompatActivity {
    private final int REQUEST_CODE_CAMERA = 100;

    public static File PathText=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/MyCameraApp/imagePathText.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //imagePathText=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/MyCameraApp/imagePathText.txt");
        if(!PathText.exists()){
            try {
                PathText.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PathText.setWritable(true);
        PathText.setReadable(true);


        setContentView(R.layout.activity_main);

    }


    public void openCamera(View view){
        Intent cameraIntent = new Intent(FullscreenActivity.this,CameraSurfaceView.class);
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);

    }


}
