package com.ankushitguy.qrscanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QrCodeResult extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.result);
        setContentView(R.layout.activity_result);


        textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("output"));
        floatingActionButton = findViewById(R.id.floatingActionButton3);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("resut",textView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(QrCodeResult.this, "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
