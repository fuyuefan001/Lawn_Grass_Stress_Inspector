package edu.purdue.fu194.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraSurfaceView extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static final int MEDIA_TYPE_VIDEO = 2;

    protected static final String TAG = null;

    private Camera mCamera;

    private CameraPreview mPreview;
    public  PictureCallback mPicture = new PictureCallback() {


        @Override

        public void onPictureTaken(byte[] data, Camera camera) {


            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if (pictureFile == null) {


                return;

            }


            try {

                FileOutputStream fos = new FileOutputStream(pictureFile);

                fos.write(data);

                fos.close();

                Log.i("cameratest", "pictureFiledata=" + data.length);

            } catch (FileNotFoundException e) {

                Log.i("cameratest", "File notfound: " + e.getMessage());

            } catch (IOException e) {

                Log.i("cameratest", "Erroraccessing file: " + e.getMessage());

            }

        }

    };

    /**
     * A safe way to get an instance of theCamera object.
     */

    public static Camera getCameraInstance() {

        Camera c = null;

        try {

            c = Camera.open(); // attempt to get a Camera instance

        } catch (Exception e) {

        }

        return c; // returns nullif camera is unavailable

    }

    private  File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(

                Environment

                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),

                "MyCameraApp");

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

                    + "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator

                    + "VID_" + timeStamp + ".mp4");

        } else {

            return null;

        }


        Intent intent=new Intent(CameraSurfaceView.this,Selection.class);

        intent.putExtra("picture",mediaFile);
        startActivity(intent);
        return mediaFile;

    }

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.camera);


// Create an instance ofCamera

        mCamera = getCameraInstance();


// Create our Preview viewand set it as the content of our activity.

        mPreview = new CameraPreview(this, mCamera);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        preview.addView(mPreview);

        Button captureButton = (Button) findViewById(R.id.takePicture);

        captureButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

// get an imagefrom the camera

                mCamera.takePicture(null, null, mPicture);



            }

        });


    }


    @Override

    protected void onStop() {

        Log.i("cameratest", "onStop");

        super.onStop(); // Always callthe superclass method first


    }

    @Override

    public void onPause() {

        super.onPause(); // Alwayscall the superclass method first

        Log.i("cameratest", "onPause");

// Release the Camera becausewe don't need it when paused

// and other activities mightneed to use it.

        if (mCamera != null) {

            mCamera.release();

            mCamera = null;

        }

    }
    @Override
    public void onResume(){
        super.onResume();
        if(mCamera==null){
            mCamera=Camera.open();
        }
    }

    /**
     * A basic Camera preview class
     */

    public class CameraPreview extends SurfaceView implements

            SurfaceHolder.Callback {

        private SurfaceHolder mHolder;

        private Camera mCamera;


        public CameraPreview(Context context, Camera camera) {

            super(context);

            mCamera = camera;


// Install aSurfaceHolder.Callback so we get notified when the

// underlyingsurface is created and destroyed.

            mHolder = getHolder();

            mHolder.addCallback(this);

// deprecatedsetting, but required on Android versions prior to 3.0

            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        }


        public void surfaceCreated(SurfaceHolder holder) {

// The Surfacehas been created, now tell the camera where to draw

// the preview.

                //mCamera=getCameraInstance();

            try {
                Camera.Parameters parameters = mCamera.getParameters();

                parameters.setFocusMode(parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦

                parameters.setWhiteBalance(parameters.WHITE_BALANCE_AUTO);
                //parameters.setAutoWhiteBalanceLock(true);
                parameters.setAutoExposureLock(true);
                parameters.setRotation(90);
                parameters.setExposureCompensation(0);
                parameters.setFlashMode("off");

                // parameters.setZoom(parameters.getMaxZoom());
                mCamera.setParameters(parameters);
                mCamera.setPreviewDisplay(holder);

                mCamera.setDisplayOrientation(90);

                mCamera.startPreview();


            } catch (IOException e) {

                Log.d(TAG, "Errorsetting camera preview: " + e.getMessage());

            }

        }


        public void surfaceDestroyed(SurfaceHolder holder) {

        }


        public void surfaceChanged(SurfaceHolder holder, int format, int w,

                                   int h) {

            if (mHolder.getSurface() == null) {

                return;

            }

            try {

                mCamera.stopPreview();

            } catch (Exception e) {

            }


            try {

                mCamera.setPreviewDisplay(mHolder);

                mCamera.setDisplayOrientation(90);

                mCamera.startPreview();


            } catch (Exception e) {

                Log.d(TAG, "Errorstarting camera preview: " + e.getMessage());

            }

        }

    }

}

