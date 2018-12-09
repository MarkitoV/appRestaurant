package com.example.markito.appproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavActivity extends AppCompatActivity {

    private Button btnRestauran;
    private Button btnCliente;
    private Button btnMenu;
    private Button btnPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        btnRestauran = (Button) findViewById(R.id.btnRestaurant);
        btnRestauran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });
        btnCliente = (Button) findViewById(R.id.btnCliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, ClientesActivity.class);
                startActivity(intent);
            }
        });
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        btnPedido = (Button) findViewById(R.id.btnPedido);
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });
    }
}
