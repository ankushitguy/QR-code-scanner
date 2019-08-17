package com.ankushitguy.qrscanner;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;
import android.widget.Toast;

public class FlashLightService extends Service {

    public FlashLightService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

}
