package com.example.markito.appproyecto;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.markito.appproyecto.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity {
    private ImageButton btnPhoto;
    private final int   CODE = 100;
    private final int   CODE_PERMISSIONS = 101;
    private ImageView   imageView;
    private EditText    txtName;
    private EditText    txtPrecio;
    private EditText    txtDescription;
    private Button      btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnPhoto = (ImageButton) findViewById(R.id.btnPhoto);

        btnPhoto.setVisibility(View.INVISIBLE);
        if (reviewPermissions()) {
            btnPhoto.setVisibility(View.VISIBLE);
        }

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                MenuActivity.this.startActivityForResult(camera, CODE);
            }
        });

        btnInsertar = (Button) findViewById(R.id.btnSiguiente);
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        txtName        = (EditText) findViewById(R.id.txtName);
        txtPrecio      = (EditText) findViewById(R.id.txtPrecio);
        txtDescription = (EditText) findViewById(R.id.txtDescription);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("authorization", Data.TOKEN);

        RequestParams params = new RequestParams();
        params.add("name", txtName.getText().toString());
        params.add("price", txtPrecio.getText().toString());
        params.add("description", txtDescription.getText().toString());

        client.post(Data.REGISTER_MENU, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //super.onSuccess(statusCode, headers, response);
                AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
                try {
                    String msn = response.getString(Integer.parseInt("msn"));
                    alertDialog.setTitle("RESPONSE SERVER");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
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
        requestPermissions(new String[] {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSIONS);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_PERMISSIONS) {
            if (permissions.length == 3) {
                btnPhoto.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            Bitmap img = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(img);
        }
    }
}
