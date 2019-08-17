package com.ankushitguy.qrscanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.joanzapata.iconify.Iconify;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.lang.reflect.Type;



public class MainActivity extends AppCompatActivity {


    ImageView button;
    FloatingActionButton qrgen;
//    TextView iconTextView;
    TextView sampleTextFont;
    Switch switch1;
    public static final boolean SWITCHON = true;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Iconify.with(new IcomoonModule());



        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);

        button = findViewById(R.id.button);
        qrgen = findViewById(R.id.qrgen);
        switch1 = findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    startService(new Intent(MainActivity.this, FlashLightService.class));
                } else {
                    stopService(new Intent(MainActivity.this, FlashLightService.class));
                }

                /*CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    if (isChecked) {
                        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                    } else cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }*/

            }
        });



//        iconTextView = findViewById(R.id.iconTextView);
//        iconTextView.setText("{icon-qrcode}");


        sampleTextFont = findViewById(R.id.sampleTextFont);
        Typeface tf = Typeface.createFromAsset(getAssets(), "icomoon.ttf");
        sampleTextFont.setTypeface(tf);


//        textView = findViewById(R.id.textView);
//        Typeface tf = Typeface.createFromAsset(getAssets(), "icomoon.ttf");
//        textView.setTypeface(tf);
//        textView.setText("\uea40");





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(false);
                //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setBarcodeImageEnabled(true);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();
            }
        });

        qrgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QrGeneratorActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,QrCodeResult.class);
                intent.putExtra("output",result.getContents().toString());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
