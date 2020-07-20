package com.northpolewebservice.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

public class CountriesAffected extends AppCompatActivity {

    EditText eTsearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;


    public static List<modelContries> modelContriesList = new ArrayList<>();
    modelContries modelContries;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_affected);


        eTsearch = findViewById(R.id.etsearch);
        listView = findViewById(R.id.ListView);
        simpleArcLoader = findViewById(R.id.loader);


        getSupportActionBar().setTitle("Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        modelContriesList.clear();
        realTimeDataFetching();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CountriesAffected.this,CountryDetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        eTsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myAdapter.getFilter().filter(s);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void realTimeDataFetching() {

        String url = "https://corona.lmao.ninja/v2/Countries";

        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");


                                modelContries = new modelContries(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);
                                modelContriesList.add(modelContries);

                            }

                            myAdapter = new MyAdapter(CountriesAffected.this,modelContriesList);
                            listView.setAdapter(myAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                        Toast.makeText(CountriesAffected.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
