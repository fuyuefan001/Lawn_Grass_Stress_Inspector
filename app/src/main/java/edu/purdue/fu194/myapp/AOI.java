package edu.purdue.fu194.myapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static edu.purdue.fu194.myapp.CameraSurfaceView.MEDIA_TYPE_IMAGE;
import static edu.purdue.fu194.myapp.CameraSurfaceView.MEDIA_TYPE_VIDEO;

/**
 * Created by Fuyuefan on 2017/8/7.
 */

public class AOI extends Activity {
    ImageView photoCaptured;
    File plant = outputPlantFile(1);
    File textSaving=new File (Environment.DIRECTORY_DOCUMENTS+"/MyCameraAppText/data.txt");
    Bitmap plantBmp;
    File white = outputWhiteFile(1);

    File picture;
    Intent cropIntent = new Intent("com.android.camera.action.CROP");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aoi);
        photoCaptured = (ImageView) findViewById(R.id.photoCaptured);
        picture = (File) getIntent().getSerializableExtra("picture");
        Bitmap bitmap = BitmapFactory.decodeFile(picture.getAbsolutePath());

        //获取从相册界面返回的缩略图
        photoCaptured.setImageBitmap(bitmap);


    }
    private void cropImage(Uri picUri,File outputFile) {
        try {


            ///File f = new File(picUri);
            //Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 1024); //512
            cropIntent.putExtra("outputY", 1024); //512
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cropIntent,0);
        }
        catch (ActivityNotFoundException e) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

//    public Intent cropImage(Uri uri){
//
//        intent.setDataAndType(uri,"image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        //intent.putExtra("outputX", 1000);
//        //intent.putExtra("outputY", 1000);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        //dirPlant="/storage/MyCameraApp/plant";
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT,saveDir);
//        intent.putExtra("noFaceDetection", true);
//        intent.putExtra("return-data", true);
//        return intent;
//    }
    public void selectPlant(View view){
        //String path=white.getAbsolutePath();
        File picture = (File) getIntent().getSerializableExtra("picture");
        Uri uri=Uri.fromFile(picture);
        cropImage(uri,plant);

        if(plant==null){
            Log.i("","Fail to pass the cropped image");
        }else{
            Log.i("",plant.getAbsolutePath());
        }



    }
    public void selectWhite(View view){
        File picture = (File) getIntent().getSerializableExtra("picture");
        Uri uri=Uri.fromFile(picture);
        cropImage(uri,white);

        if(white==null){
            Log.i("666666666666666","Fail to pass the cropped image");
        }else{
            Log.i("666666666666666",white.getAbsolutePath());
        }
    }
    private  File outputWhiteFile(int type) {

        File mediaStorageDir = new File(

                Environment

                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),

                "MyCameraApp/white");

        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Log.d("MyCameraApp", "failed tocreate directory");

                return null;

            }

        }


// Create a media file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")

                .format(new Date());

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator

                    + "IMG!" + timeStamp + ".bmp");

        }  else {

            return null;

        }
        return mediaFile;

    }
    private  File outputPlantFile(int type) {

        File mediaStorageDir = new File(

                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp/plant");

        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Log.d("MyCameraApp", "failed tocreate directory");

                return null;

            }

        }


// Create a media file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")

                .format(new Date());

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator

                    + "IMG!" + timeStamp + ".jpg");

        }  else {

            return null;

        }
        return mediaFile;

    }
    public void toAnalyze(View view){
        Intent intent = new Intent(AOI.this,Analyze.class);
        intent.putExtra("plantPath",plant.getAbsolutePath());
        intent.putExtra("whitePath",white.getAbsolutePath());
        intent.putExtra("picturePath",picture.getAbsolutePath());
        startActivityForResult(intent,0);

    }
}
