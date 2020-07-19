package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Polar;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.scales.Linear;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.PolarSeriesType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.ScaleTypes;
import com.anychart.enums.TooltipDisplayMode;
import com.example.coviddata.Objetos.Reporte;
import com.example.coviddata.R;
import com.example.coviddata.Respuestas.RespuestaWSDataRegiones;
import com.example.coviddata.Servicio.ServicioWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRegionales extends AppCompatActivity {
    private ServicioWeb servicioWeb;
   private AnyChartView anyChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data_regionales);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        servicioWeb = retrofit.create(ServicioWeb.class);
        servicio();
    }

    public void servicio(){

        final Call<RespuestaWSDataRegiones> respuestaWSDataRegionesCall = servicioWeb.dataRegiones();
        respuestaWSDataRegionesCall.enqueue(new Callback<RespuestaWSDataRegiones>() {
            @Override
            public void onResponse(Call<RespuestaWSDataRegiones> call, Response<RespuestaWSDataRegiones> response) {
                if (response != null){
                    RespuestaWSDataRegiones respuestaWSDataRegiones = response.body();
                    Log.d("Retrofit", respuestaWSDataRegiones.toString());

                    Reporte reportes[] = respuestaWSDataRegiones.getReporte();
                    Log.d("Retrofit", reportes[0].toString());
                    generarGrafico2(reportes);
                    generarGrafico1(reportes);

                }
            }
            @Override
            public void onFailure(Call<RespuestaWSDataRegiones> call, Throwable t) {
                Log.d("Retrofit", "explotó");

            }
        });

    }

    public void generarGrafico1 (Reporte reportes[]){
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Polar polar = AnyChart.polar();
        List<DataEntry> data = new ArrayList<>();
        String nombre;
        for (int i = 0; i < reportes.length; i++) {
            if (i == 7) {
                nombre = "O Higgins";
            } else {
                nombre = reportes[i].getRegion();
            }
            if (!nombre.equalsIgnoreCase("Metropolitana")) {
                Log.d("Names", reportes[i].getRegion());
                data.add(new CustomDataEntry(nombre, reportes[i].getCasos_nuevos_snotificar(),
                        reportes[i].getCasos_nuevos_ssintomas(), reportes[i].getCasos_nuevos_csintomas(), reportes[i].getCasos_nuevos_total()));
            }

        }
        Set set = Set.instantiate();
        set.data(data);
        Mapping series1Data = set.mapAs("{ x: 'x', title: 'title1', value: 'value' }");
        Mapping series2Data = set.mapAs("{ x: 'x', title: 'title2', value: 'value2' }");
        Mapping series3Data = set.mapAs("{ x: 'x', title: 'title3', value: 'value3' }");
        Mapping series4Data = set.mapAs("{ x: 'x', title: 'title4', value: 'value4' }");
        polar.column(series1Data);
        polar.column(series2Data);
        polar.column(series3Data);
        polar.column(series4Data);
        polar.title().fontColor("#CEE8F2");
        String color[] = {"#ffbe0b", "#f77f00", "#00b4d8", "#80b918"};
        polar.palette(color);
        polar.background("#102530");
        polar.labels().fontColor("#CEE8F2");
        polar.getSeriesAt(0).name("Sin Notificar");
        polar.getSeriesAt(1).name("Sin Sintomas");
        polar.getSeriesAt(2).name("Con Sintomas");
        polar.getSeriesAt(3).name("Nuevos Totales");


        //polar.legend().itemsFormat("{%seriesName} caca");
        polar.legend(true);
        polar.legend().fontColor("#CEE8F2");

        polar.legend().position("center-bottom").itemsLayout(LegendLayout.VERTICAL).align(Align.CENTER);

        polar.sortPointsByX(true)
                .defaultSeriesType(PolarSeriesType.COLUMN)
                .yAxis(false)
                .xScale(ScaleTypes.ORDINAL);

        polar.innerRadius(20);
        ((Linear) polar.yScale(Linear.class)).stackMode(ScaleStackMode.VALUE);
        polar.tooltip().format("{%title} {%value}")
                .displayMode(TooltipDisplayMode.UNION);
        anyChartView.setChart(polar);

    }
    private void generarGrafico2(Reporte reportes[]){
        AnyChartView anyChartView1 = findViewById(R.id.any_chart_view1);
        anyChartView1.setProgressBar(findViewById(R.id.progress_bar1));
        APIlib.getInstance().setActiveAnyChartView (anyChartView1);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < reportes.length; i++) {
            if (reportes[i].getRegion().equalsIgnoreCase("Metropolitana")) {
              data.add(new ValueDataEntry("Sin notificar", reportes[i].getCasos_nuevos_snotificar()));
              data.add(new ValueDataEntry("Sin sintomas", reportes[i].getCasos_nuevos_ssintomas()));
                data.add(new ValueDataEntry("Con sintomas", reportes[i].getCasos_nuevos_csintomas()));
                data.add(new ValueDataEntry("Totales", reportes[i].getCasos_nuevos_total()));
            }

        }
        Column column = cartesian.column(data);
        column.color("#ffbe0b");
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.AUTO)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title().fontColor("#CEE8F2");
        cartesian.labels().fontColor("#CEE8F2");
        cartesian.background("#102530");
        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        anyChartView1.setChart(cartesian);

    }


    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("title1", "Sin notificar");
            setValue("title2","Sin sintomas" );
            setValue("title3","Con Sintomas" );
            setValue("title4","Nuevos totales" );
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }




    private void initMain(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initMain();
        }
        return super.onKeyDown(keyCode, event);

    }


}
