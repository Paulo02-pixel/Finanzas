package com.example.finanzas.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finanzas.Helper.ManagmentCart;
import com.example.finanzas.R;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivityCredit extends AppCompatActivity {

    private SeekBar seekBarInterestRate, seekBarPeriods;
    private TextView textViewInterestRate, textViewPeriods, textViewCreditResult;
    private RadioGroup radioGroupRateType, radioGroupMethodType;
    TextView totalFeeText;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        managmentCart = new ManagmentCart(this);
        totalFeeText = findViewById(R.id.totalFeeText);
        String nombre = getIntent().getStringExtra("nombre");
        double itemTotal = (double) Math.round(managmentCart.getTotalFee() * 100) /100;
        totalFeeText.setText("Monto a pagar: S/ " + itemTotal);

        seekBarInterestRate = findViewById(R.id.seekBarInterestRate);
        seekBarPeriods = findViewById(R.id.seekBarPeriods);
        textViewInterestRate = findViewById(R.id.textViewInterestRate);
        textViewPeriods = findViewById(R.id.textViewPeriods);
        textViewCreditResult = findViewById(R.id.textViewCreditResult);
        radioGroupRateType = findViewById(R.id.radioGroupRateType);
        radioGroupMethodType = findViewById(R.id.radioGroupMethodType);

        seekBarInterestRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double interestRate = 0.5 + (progress / 10.0);
                textViewInterestRate.setText(String.format("Tasa de Interés Anual: %.1f%%", interestRate));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBarPeriods.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int periods = 1 + progress;
                textViewPeriods.setText(String.format("Número de Plazos: %d", periods));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        Button buttonCalculateCredit = findViewById(R.id.buttonCalculateCredit);
        buttonCalculateCredit.setOnClickListener(v -> calculateCredit(itemTotal));

        ImageButton buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> finish());

    }
    private void calculateCredit(double itemTotal) {
        //obtener tasa y periodos:
        double interestRate = 0.5 + (seekBarInterestRate.getProgress() / 10.0);
        int periods = 1 + seekBarPeriods.getProgress();

        //convertir la tasa a efectiva:
        if (radioGroupRateType.getCheckedRadioButtonId() == R.id.radioNominal){
            interestRate = 100*(Math.pow((1+((interestRate/100)/360)),360)-1);
        }

        //convertir la tasa efectiva a mensual
        double tasa = 100*(Math.pow(1+(interestRate/100),(30.0/360.0))-1);

        double[] cuotas = new double[periods];
        double saldo = itemTotal;
        double intereses, cuota, amortizacion = 0;
        StringBuilder summary = new StringBuilder();
        summary.append("Detalles del Crédito:\n\n");
        int selectedMethodId = radioGroupMethodType.getCheckedRadioButtonId();
        if (selectedMethodId == R.id.radioMethodGerman) {
            //MÉTODO ALEMÁN:
            for(int i = 0;i<periods;i++){
                intereses = saldo*(tasa/100);
                amortizacion = saldo/(periods-(i+1)+1);
                cuota = intereses+amortizacion;
                cuotas[i] = cuota;
                saldo -= amortizacion;
                summary.append(String.format(Locale.getDefault(),
                        "Periodo %d:\nIntereses: S/ %.2f\nCuota: S/ %.2f\nAmortización: S/ %.2f\nSaldo: S/ %.2f\n\n",
                        (i + 1), intereses, cuota, amortizacion, saldo));
            }
        }else if (selectedMethodId == R.id.radioMethodFrench){
            //MÉTODO FRANCES:
            for(int i = 0;i<periods;i++){
                intereses = saldo*(tasa/100);
                cuota = (saldo * (tasa/100))/(1 - (Math.pow(1 + (tasa/100), -(periods-i))));
                cuotas[i] = cuota;
                amortizacion = cuota-intereses;
                saldo -= amortizacion;
                summary.append(String.format(Locale.getDefault(),
                "Periodo %d:\nIntereses: S/ %.2f\nCuota: S/ %.2f\nAmortización: S/ %.2f\nSaldo: S/ %.2f\n\n",
                (i + 1), intereses, cuota, amortizacion, saldo));
            }
        }else{
            //MÉTODO AMERICANO:
            for(int i = 0;i<periods;i++){
                intereses = saldo*(tasa/100);
                if(i==periods-1){amortizacion=saldo;}
                cuota = intereses+amortizacion;
                cuotas[i] = cuota;
                saldo = saldo- amortizacion;
                summary.append(String.format(Locale.getDefault(),
                "Periodo %d:\nIntereses: S/ %.2f\nCuota: S/ %.2f\nAmortización: S/ %.2f\nSaldo: S/ %.2f\n\n",
                (i + 1), intereses, cuota, amortizacion, saldo));
            }
        }
        textViewCreditResult.setText(summary.toString());
    }
}