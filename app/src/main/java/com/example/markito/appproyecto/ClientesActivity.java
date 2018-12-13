package com.example.markito.appproyecto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markito.appproyecto.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ClientesActivity extends AppCompatActivity {

    private Button REGISTRAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        REGISTRAR = (Button) findViewById(R.id.btnRegistroC);
        REGISTRAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        TextView name;
        TextView email;
        TextView phone;
        TextView ci;
        TextView password;
        TextView pass2;

        name     = (TextView) findViewById(R.id.txtNombreC);
        email    = (TextView) findViewById(R.id.txtEmailC);
        phone    = (TextView) findViewById(R.id.txtPhoneC);
        ci       = (TextView) findViewById(R.id.txtCedulaC);
        password = (TextView) findViewById(R.id.txtPasswordC);
        pass2    = (TextView) findViewById(R.id.txtConfirmPass);

        if (password.getText().toString().equals(pass2.getText().toString())) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("name", name.getText().toString());
            params.add("email", email.getText().toString());
            params.add("phone", phone.getText().toString());
            params.add("ci", ci.getText().toString());
            params.add("password", password.getText().toString());
            client.post(Data.ADD_CLIENT, params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //super.onSuccess(statusCode, headers, response);
                    Toast.makeText(ClientesActivity.this, "Cliente Registrado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ClientesActivity.this, NavActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(ClientesActivity.this,"Contraseña Incorrecta", Toast.LENGTH_LONG).show();
        }
    }
}
