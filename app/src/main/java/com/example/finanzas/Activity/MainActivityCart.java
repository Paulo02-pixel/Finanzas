package com.example.finanzas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finanzas.Adapter.CartAdapter;
import com.example.finanzas.Helper.ManagmentCart;
import com.example.finanzas.databinding.ActivityCartBinding;

public class MainActivityCart extends AppCompatActivity {
    private ManagmentCart managmentCart;
    ActivityCartBinding binding;
    double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

        String nombre = getIntent().getStringExtra("nombre");

        setVariable();
        initlist(nombre);
        calculatorCart();
    }

    private void initlist(String nombre) {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
            binding.emptyText.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivityCart.this, MainActivity.class);
                intent.putExtra("nombre", nombre);
                startActivity(intent);
            });
        }
        else{
            binding.emptyText.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), () -> calculatorCart()));
    }



    private void calculatorCart(){
        double percetTax = 0.02;
        double delivery = 10;

        tax = (double) Math.round(managmentCart.getTotalFee() * percetTax * 100) /100;

        double total = (double) Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = (double) Math.round(managmentCart.getTotalFee() * 100) /100;
        binding.totalFeeText.setText("S/"+ itemTotal);
        binding.taxText.setText("S/"+ tax);
        binding.deliveryText.setText("S/"+delivery);
        binding.totalText.setText("S/"+ total);
    }
    private void setVariable() {
        binding.backBtn1.setOnClickListener(v -> finish());
    }
}