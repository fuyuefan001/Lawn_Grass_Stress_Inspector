package edu.purdue.fu194.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Fuyuefan on 2017/8/7.
 */
public class Selection extends AppCompatActivity{
    private final int REQUEST_CODE_CAMERA = 100;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        ImageView photoCaptured;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        photoCaptured=(ImageView)findViewById(R.id.photoCaptured);
        File picture=(File)getIntent().getSerializableExtra("picture");
        Bitmap bitmap=BitmapFactory.decodeFile(picture.getAbsolutePath());
        photoCaptured.setImageBitmap(bitmap);
        //setContentView(R.layout.selection);

    }
    public void retake(View v){
        Intent cameraIntent = new Intent(Selection.this,CameraSurfaceView.class);
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }
    public void usePhoto(View v){
        Intent intent=new Intent(Selection.this,AOI.class);
        File pictureCaptured=(File)getIntent().getSerializableExtra("picture");
        intent.putExtra("picture",pictureCaptured);
        startActivity(intent);
    }
}




