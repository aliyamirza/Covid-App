package com.example.hp.covidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    EditText edSearch;
    ListView listView;
    SimpleArcLoader arcL;

    public static List<Model>modelList=new ArrayList<>();
    Model model;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        edSearch = (EditText) findViewById(R.id.edSearch);
        listView = (ListView) findViewById(R.id.listView);
        arcL = (SimpleArcLoader) findViewById(R.id.arcL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Affected Countries");

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),ShowCountryData.class).putExtra("position",position));
            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

            String url = "https://corona.lmao.ninja/v2/countries";
            arcL.start();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String countryName = jsonObject.getString("country");
                            String cases = jsonObject.getString("cases");
                            String todayCases = jsonObject.getString("todayCases");
                            String deaths = jsonObject.getString("deaths");
                            String todayDeaths = jsonObject.getString("todayDeaths");
                            String recovered = jsonObject.getString("recovered");
                            String active = jsonObject.getString("active");
                            String critical = jsonObject.getString("critical");

                            JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flagUrl = object.getString("flag");

                            model = new Model(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);
                            modelList.add(model);

                        }
                        adapter = new Adapter(AffectedCountries.this,modelList);
                        listView.setAdapter(adapter);
                        arcL.stop();
                        arcL.setVisibility(View.GONE);



                    } catch (JSONException e) {
                        e.printStackTrace();
                        arcL.stop();
                        arcL.setVisibility(View.GONE);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    arcL.stop();
                    arcL.setVisibility(View.GONE);
                    Toast.makeText(AffectedCountries.this,error.getMessage(),Toast.LENGTH_LONG).show();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);


        }
    }
