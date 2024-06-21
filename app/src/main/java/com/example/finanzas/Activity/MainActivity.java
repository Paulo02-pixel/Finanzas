package com.example.finanzas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finanzas.Adapter.PopularAdapter;
import com.example.finanzas.R;
import com.example.finanzas.databinding.ActivityMainBinding;
import com.example.finanzas.domain.PopularDomain;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombre = getIntent().getStringExtra("nombre");
        binding.textView5.setText(nombre);
        ImageButton imageButton17 = findViewById(R.id.imageButton17);
        imageButton17.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivityProfile.class);
            startActivity(intent);
        });
        initRecyclerView();
        bottomNavigation(nombre);
    }

    private void bottomNavigation(String nombre) {
        binding.cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivityChoose.class);
            intent.putExtra("nombre", nombre);
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();
        items.add(new PopularDomain("Something Special", "something", 15, 4.5, 40, "Whisky bueno"));
        items.add(new PopularDomain("Green Label", "green_label", 15, 4.8, 90, "Whisky muy bueno"));
        items.add(new PopularDomain("Smirnoff", "smirnoff", 15, 4.3, 20, "Vodka bueno"));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }

}