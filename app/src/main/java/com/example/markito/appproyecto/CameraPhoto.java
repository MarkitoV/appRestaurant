package com.example.markito.appproyecto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CameraPhoto extends AppCompatActivity {
    private final int CODE=100;
    private ImageView IMG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo);
        ImageButton btn = findViewById(R.id.camera);
        IMG= findViewById(R.id.image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CameraPhoto.this.startActivityForResult(camera,CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE){
            Bitmap img=(Bitmap) data.getExtras().get("data");
            IMG.setImageBitmap(img);
        }
    }
}
