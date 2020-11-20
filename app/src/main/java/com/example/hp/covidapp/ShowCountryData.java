package com.example.hp.covidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowCountryData extends AppCompatActivity {

    private int positionCountry;
    TextView tvCountry,tvCases,tvRecJ,tvCriJ,tvActJ,tvTodayCases,tvTodayDeaths,tvTotalDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_country_data);


        Intent intent=getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Country Details");

        tvCountry= (TextView) findViewById(R.id.tvCountry);
        tvCases= (TextView) findViewById(R.id.tvCases);
        tvRecJ= (TextView) findViewById(R.id.tvRec);
        tvCriJ= (TextView) findViewById(R.id.tvCri);
        tvActJ= (TextView) findViewById(R.id.tvAct);
        tvTodayCases= (TextView) findViewById(R.id.tvTodayCases);
        tvTodayDeaths= (TextView) findViewById(R.id.tvTodayDeaths);
        tvTotalDeaths= (TextView) findViewById(R.id.tvTotalDeaths);

        tvCountry.setText(AffectedCountries.modelList.get(positionCountry).getCountry());
        tvCases.setText(AffectedCountries.modelList.get(positionCountry).getCases());
        tvRecJ.setText(AffectedCountries.modelList.get(positionCountry).getRecovered());
        tvCriJ.setText(AffectedCountries.modelList.get(positionCountry).getCritical());
        tvActJ.setText(AffectedCountries.modelList.get(positionCountry).getActive());
        tvTodayCases.setText(AffectedCountries.modelList.get(positionCountry).getTodayCases());
        tvTodayDeaths.setText(AffectedCountries.modelList.get(positionCountry).getTodayDeaths());
        tvTotalDeaths.setText(AffectedCountries.modelList.get(positionCountry).getDeaths());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

}
