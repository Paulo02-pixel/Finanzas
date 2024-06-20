package com.example.finanzas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finanzas.Helper.ManagmentCart;
import com.example.finanzas.R;
import com.example.finanzas.databinding.ActivityChooseBinding;

public class MainActivityChoose extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        String nombre = getIntent().getStringExtra("nombre");

        backMainActivity(nombre);
        goToMainActivityCart(nombre);
    }

    private void goToMainActivityCart(String nombre) {
        Button button = findViewById(R.id.prepaidBtn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityChoose.this, MainActivityCart.class);
            intent.putExtra("nombre", nombre);
            startActivity(intent);
        });
    }

    private void backMainActivity(String nombre) {
        ImageView imageView = findViewById(R.id.backBtn2);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityChoose.this, MainActivity.class);
            intent.putExtra("nombre", nombre);
            startActivity(intent);
        });
    }

}