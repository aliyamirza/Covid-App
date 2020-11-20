package com.example.hp.covidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvCases,tvRecJ,tvCriJ,tvActJ,tvTdCJ,tvTdDJ,tvTodayDJ,tvAfCJ;
    SimpleArcLoader loader;
    PieChart pieChartJ;
    ScrollView scrollImg;
    Button btnTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrack= (Button) findViewById(R.id.btnTrack);

        tvCases= (TextView) findViewById(R.id.tvCases);
        tvRecJ= (TextView) findViewById(R.id.tvRec);
        tvCriJ= (TextView) findViewById(R.id.tvCri);
        tvActJ= (TextView) findViewById(R.id.tvAct);
        tvTdCJ= (TextView) findViewById(R.id.tvTdC);
        tvTdDJ= (TextView) findViewById(R.id.tvTdD);
        tvTodayDJ= (TextView) findViewById(R.id.tvTodayD);
        tvAfCJ= (TextView) findViewById(R.id.tvAfC);

        loader= (SimpleArcLoader) findViewById(R.id.loader);
        pieChartJ= (PieChart) findViewById(R.id.pieChart);
        scrollImg= (ScrollView) findViewById(R.id.scrollImg);

        fetchData();

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AffectedCountries.class);
                startActivity(i);
            }
        });


    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all";
        loader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecJ.setText(jsonObject.getString("recovered"));
                    tvCriJ.setText(jsonObject.getString("critical"));
                    tvActJ.setText(jsonObject.getString("active"));
                    tvTdCJ.setText(jsonObject.getString("todayCases"));
                    tvTdDJ.setText(jsonObject.getString("deaths"));
                    tvTodayDJ.setText(jsonObject.getString("todayDeaths"));
                    tvAfCJ.setText(jsonObject.getString("affectedCountries"));

                    pieChartJ.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChartJ.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecJ.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChartJ.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTdDJ.getText().toString()), Color.parseColor("#EF5350")));
                    pieChartJ.addPieSlice(new PieModel("Active",Integer.parseInt(tvActJ.getText().toString()),Color.parseColor("#29B6F6")));
                    pieChartJ.startAnimation();

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollImg.setVisibility(View.VISIBLE);

                } catch (JSONException e) {

                    e.printStackTrace();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollImg.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loader.stop();
                loader.setVisibility(View.GONE);
                scrollImg.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


}
