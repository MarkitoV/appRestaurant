package com.example.markito.appproyecto;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.markito.appproyecto.utils.BitmapStruct;
import com.example.markito.appproyecto.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class CameraPhoto extends AppCompatActivity {
    private final int    CODE=100;
    private final int    CODE_PERMISSION = 101;
    private ImageView    IMG;
    private ImageButton  BTN;
    private Button       SEND;
    private BitmapStruct DATA_IMAGE;
    private String ID_RESTAURANT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo);
        BTN  = findViewById(R.id.camera);
        SEND = findViewById(R.id.register);
        IMG  = findViewById(R.id.image);

        BTN.setVisibility(View.INVISIBLE);
        if (reviewPermissions()) {
            BTN.setVisibility(View.VISIBLE);
        }

        BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CameraPhoto.this.startActivityForResult(camera,CODE);
            }
        });

        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DATA_IMAGE != null) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    File img = new File(DATA_IMAGE.path);
                    client.addHeader("authorization", Data.TOKEN);
                    RequestParams params = new RequestParams();
                    //try {
                        params.add("picture", DATA_IMAGE.path);
                        params.put("id", ID_RESTAURANT);
                        client.post(Data.ADD_REST_IMG + "?id=" + ID_RESTAURANT, params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                //super.onSuccess(statusCode, headers, response);
                                Toast.makeText(CameraPhoto.this, "Guardado con Exito", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                                startActivity(intent);
                            }
                        });
                    //} catch (FileNotFoundException e) {
                    //    e.printStackTrace();
                    //}
                }
            }
        });
    }

    private boolean reviewPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, CODE_PERMISSION);
        return  false;
    }



    private BitmapStruct saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String path    = directory.getAbsolutePath() + "/profile.jpg";
        BitmapStruct p = new BitmapStruct();
        p.img  = BitmapFactory.decodeFile(path);
        p.path = path;
        return p;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION == requestCode) {
            if (permissions.length == 3) {
                BTN.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE){
            Bitmap img=(Bitmap) data.getExtras().get("data");
            DATA_IMAGE = saveToInternalStorage(img);
            IMG.setImageBitmap(img);
        }
    }
}
