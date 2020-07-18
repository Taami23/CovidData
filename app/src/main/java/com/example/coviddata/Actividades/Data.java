package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import com.anychart.charts.Pie;

import com.anychart.enums.Align;

import com.anychart.enums.LegendLayout;
import com.example.coviddata.R;

import java.util.ArrayList;
import java.util.List;

public class Data extends AppCompatActivity {
    AnyChartView anyChartView;
    TextView titulo;
    TextView titulo2;
    TextView titulo3;
    TextView titulo4;
    Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data);
        titulo = findViewById(R.id.titulo);
        titulo2 = findViewById(R.id.titulo2);
        titulo3 = findViewById(R.id.titulo3);
        titulo4 = findViewById(R.id.titulo4);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        pie = AnyChart.pie();

        mostarDatos();
    }

    private void mostarDatos(){
        SharedPreferences preferences = getSharedPreferences(MostrarRegiones.CREDENTIALS, MODE_PRIVATE);
        String info = preferences.getString("info", "no encontrado");
        String fecha= preferences.getString("fecha", "no encontrado");
        int acumulado_total = preferences.getInt("acumulado_total", 0);
        int casos_nuevos_total = preferences.getInt("casos_nuevos_total", 0);
        int casos_nuevos_csintomas = preferences.getInt("casos_nuevos_csintomas", 0);
        int casos_nuevos_ssintomas = preferences.getInt("casos_nuevos_ssintomas", 0);
        int casos_nuevos_snotificar = preferences.getInt("casos_nuevos_snotificar", 0);
        int fallecidos = preferences.getInt("fallecidos", 0);
        int casos_activos_confirmados = preferences.getInt("casos_activos_confirmados", 0);
        Log.d("Data", preferences.getString("info", "no encontrado"));


        titulo.setText(info.toUpperCase());
        titulo2.setText("Acumulado Total: "+ acumulado_total);
        titulo3.setText("Casos nuevos totales: " + casos_nuevos_total);
        titulo4.setText("Casos activos: " + casos_activos_confirmados);

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Nuevos con Sintomas", casos_nuevos_csintomas));
        data.add(new ValueDataEntry("Nuevos sin Sintomas", casos_nuevos_ssintomas));
        data.add(new ValueDataEntry("Nuevos sin Notificar", casos_nuevos_snotificar));
        data.add(new ValueDataEntry("Fallecidos", fallecidos));

        pie.data(data);
        pie.innerRadius("70%");

        pie.title("Holi");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Casos")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.VERTICAL)
                .align(Align.LEFT);

        anyChartView.setChart(pie);

    }

    private void initMostrarRegiones(){
        Intent regiones = new Intent(this, MostrarRegiones.class);
        startActivity(regiones);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initMostrarRegiones();
        }
        return super.onKeyDown(keyCode, event);
    }
}





