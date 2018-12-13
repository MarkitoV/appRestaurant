package com.example.markito.appproyecto;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.markito.appproyecto.adapters.MenuAdapter;
import com.example.markito.appproyecto.items.ItemMenu;
import com.example.markito.appproyecto.utils.BitmapStruct;
import com.example.markito.appproyecto.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity {
    private ImageButton  btnPhoto;
    private final int    CODE = 100;
    private final int    CODE_PERMISSIONS = 101;
    private ImageView    imageView;
    private EditText     txtName;
    private EditText     txtPrecio;
    private EditText     txtDescription;
    private Button       btnInsertar;
    private String       PATH_IMAGE;
    ArrayList<ItemMenu>  listData;
    private RecyclerView recyclerView;
    private String       ID_RESTAURANT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnPhoto = (ImageButton) findViewById(R.id.btnPhotoM);

        btnPhoto.setVisibility(View.INVISIBLE);
        if (reviewPermissions()) {
            btnPhoto.setVisibility(View.VISIBLE);
        }

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = createFile();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri url = FileProvider.getUriForFile(MenuActivity.this, "com.example.markito.appproyecto.provider", file);
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, url);

                } else {
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                MenuActivity.this.startActivityForResult(camera, CODE);
            }
        });

        btnInsertar = (Button) findViewById(R.id.btnInsertarM);
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private File createFile(){
        File file = new File(Environment.getExternalStorageDirectory(), "images/misimagenes");
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = "";
        if (file.exists()) {
            name = "IMG_" + System.currentTimeMillis() / 1000 + ".jpg";
        }
        PATH_IMAGE = file.getAbsolutePath() + File.separator + name;
        File newfile = new File(PATH_IMAGE);
        return newfile;
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
                //String msn = response.getString("msn");
                String msn = "Datos insertados";
                alertDialog.setTitle("RESPONSE SERVER");
                alertDialog.setMessage(msn);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });
        if (PATH_IMAGE != null) {
            File img = new File(PATH_IMAGE);
        } else {
            Toast.makeText(this, "Guardando sin imagen", Toast.LENGTH_SHORT);
        }
    }


    //no se usa, todavia
    private void getData() {
        listData.clear();
        AsyncHttpClient listaMenu = new AsyncHttpClient();
        listaMenu.get(Data.SHOW_MENU, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                String id;
                String idrestaurant;
                String name;
                String price;
                String description;
                String picture = "";
                try {
                    JSONArray data = response.getJSONArray("result");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        id = item.getString("id");
                        idrestaurant = item.getString("idrestaurant");
                        name = item.getString("name");
                        price = item.getString("price");
                        description = item.getString("description");
                        if (item.has("picture")) {
                            picture = item.getString("picture");
                        }
                        Log.i("IMG", item.getString("picture"));
                        listData.add(new ItemMenu(id, idrestaurant, name, price, picture));
                    }
                    loadData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //no se usa todavia
    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        MenuAdapter adapter = new MenuAdapter(this, listData);
        recyclerView.setAdapter(adapter);
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
            //Bitmap img = (Bitmap) data.getExtras().get("data");
            Bitmap img = BitmapFactory.decodeFile(PATH_IMAGE);
            imageView.setImageBitmap(img);
        }
    }
}
