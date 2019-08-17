package com.ankushitguy.qrscanner;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


public class QrGeneratorActivity extends AppCompatActivity {


    public static final int MY_PERMISSION_READ_WRITE_EXTERNAL_STORAGE = 1;

    //Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_READ_WRITE_EXTERNAL_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(QrGeneratorActivity.this);
                    builder.setTitle("Ooooops!")
                            .setMessage("Permission required for saving generated QRcode to local Storage!")
                            .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                     ActivityCompat.requestPermissions(QrGeneratorActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_WRITE_EXTERNAL_STORAGE);
//                                    Toast.makeText(QrGeneratorActivity.this, "You Chose Yes", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Toast.makeText(this, "Denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }

    //Permission
    public void permissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int writePermissionRequest = ContextCompat.checkSelfPermission(QrGeneratorActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermissionRequest = ContextCompat.checkSelfPermission(QrGeneratorActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writePermissionRequest!=PackageManager.PERMISSION_GRANTED && readPermissionRequest!=PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(QrGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(QrGeneratorActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(QrGeneratorActivity.this);
                    builder.setTitle("NOTICE")
                            .setMessage("Permission required for saving generated QRcode to local Storage!")
                            .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(QrGeneratorActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_WRITE_EXTERNAL_STORAGE);
//                                    Toast.makeText(QrGeneratorActivity.this, "You Chose Yes", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    ActivityCompat.requestPermissions(QrGeneratorActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_WRITE_EXTERNAL_STORAGE);
                }

            }else {
                return;
//                Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Create bitmap
    public static Bitmap createBitMap(String encodeText,int WIDTH,int HEIGHT) throws WriterException {
        String text = encodeText;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix matrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(matrix);
        return bitmap;
    }


    //Save bitmap to storage
    public void saveImage(Bitmap finalBitmap) {
        try {
            String mTimeStamp = new SimpleDateFormat("dd_MM_yyyy_HHmm_SS").format(new Date());

            String mImageName = "QR_"+mTimeStamp+".png";

            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            File myDir = new File(root + "/QRCODE_IMAGES");
            myDir.mkdirs();
            String pathName = myDir.toString()+File.separator+mImageName;
            FileOutputStream out = new FileOutputStream(pathName);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            Toast.makeText(QrGeneratorActivity.this, String.format("Saved to : %s",pathName), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    ImageView displayQr;
    EditText valueText;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.qrgenerator);
        setContentView(R.layout.activity_qr_generator);

        permissionRequest();
        displayQr = findViewById(R.id.displayQr);
        valueText = findViewById(R.id.valueText);
        floatingActionButton = findViewById(R.id.floatingActionButton);




        valueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Generator
                if (!valueText.getText().toString().isEmpty()) {
                    displayQr.setVisibility(View.VISIBLE);

                    try {
                        displayQr.setImageBitmap(createBitMap(valueText.getText().toString(), 300, 300));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }


//                    Toast.makeText(QrGeneratorActivity.this, valueText.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    displayQr.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int writePermissionRequest = ContextCompat.checkSelfPermission(QrGeneratorActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int readPermissionRequest = ContextCompat.checkSelfPermission(QrGeneratorActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (!valueText.getText().toString().isEmpty()) {
                    try {

                        saveImage(createBitMap(valueText.getText().toString(), 300, 300));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(QrGeneratorActivity.this, "Empty Text!", Toast.LENGTH_SHORT).show();
                }
                if (writePermissionRequest!=PackageManager.PERMISSION_GRANTED && readPermissionRequest!=PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(QrGeneratorActivity.this, "Failed: Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    }
