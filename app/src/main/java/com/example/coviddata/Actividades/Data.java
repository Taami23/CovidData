package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import com.anychart.charts.Pie;

import com.anychart.enums.Align;

import com.anychart.enums.LegendLayout;
import com.example.coviddata.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Data extends AppCompatActivity {
    AnyChartView anyChartView;
    TextView titulo;
    TextView titulo2;
    TextView titulo3;
    TextView titulo4;
    TextView titulo5;
    Pie pie;
    Integer vengoDe;
    private TextView fecha1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data);
        titulo = findViewById(R.id.titulo);
        titulo2 = findViewById(R.id.titulo2);
        titulo3 = findViewById(R.id.titulo3);
        titulo4 = findViewById(R.id.titulo4);
        titulo5 = findViewById(R.id.titulo5);
        fecha1 = findViewById(R.id.soyTitleyFecha);
        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setBackgroundColor("#102530");

        pie = AnyChart.pie();

        mostarDatos();
    }

    private void mostarDatos(){
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        SharedPreferences preferences = getSharedPreferences(MainActivity.CREDENTIALS, MODE_PRIVATE);
        String info = preferences.getString("info", "no encontrado");
        String fecha = preferences.getString("fecha", "no encontrado");
        int acumulado_total = preferences.getInt("acumulado_total", 0);
        int casos_nuevos_total = preferences.getInt("casos_nuevos_total", 0);
        int casos_nuevos_csintomas = preferences.getInt("casos_nuevos_csintomas", 0);
        int casos_nuevos_ssintomas = preferences.getInt("casos_nuevos_ssintomas", 0);
        int casos_nuevos_snotificar = preferences.getInt("casos_nuevos_snotificar", 0);
        int fallecidos = preferences.getInt("fallecidos", 0);
        int casos_activos_confirmados = preferences.getInt("casos_activos_confirmados", 0);
        vengoDe = preferences.getInt("Actividad", 0);
        Log.d("Data", preferences.getString("info", "no encontrado"));
        Locale locale = new Locale("es", "ES");
        fecha1.setText("Datos actualizados al: "+fecha);
        titulo.setText(info.toUpperCase());
        titulo2.setText("Acumulado Total: "+ NumberFormat.getInstance(locale).format(acumulado_total));
        titulo3.setText("Casos nuevos totales: " + NumberFormat.getInstance(locale).format(casos_nuevos_total));
        titulo4.setText("Casos activos: " + NumberFormat.getInstance(locale).format(casos_activos_confirmados));
        titulo5.setText("Fallecidos: " + NumberFormat.getInstance(locale).format(fallecidos));

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Nuevos con Sintomas", casos_nuevos_csintomas));
        data.add(new ValueDataEntry("Nuevos sin Sintomas", casos_nuevos_ssintomas));
        data.add(new ValueDataEntry("Nuevos sin Notificar", casos_nuevos_snotificar));

        pie.data(data);
        pie.innerRadius("70%");
        pie.container("container");
        pie.draw(true);


        pie.background("#102530");
        pie.labels().position("outside");
        pie.labels().fontColor("#CEE8F2");

        pie.legend().fontColor("#CEE8F2");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Casos")
                .padding(0d, 0d, 10d, 0d);
        String color[] = {"#ffbe0b", "#f77f00", "#00b4d8", "#e6ee9c"};
        pie.palette(color);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.CENTER);
        anyChartView.setChart(pie);

    }
    private void initMostrarRegiones(){
        Intent regiones = new Intent(this, MostrarRegiones.class);
        startActivity(regiones);
        finish();
    }
    private void initMain(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (vengoDe == 1){
                initMain();
            }else{
                initMostrarRegiones();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}





