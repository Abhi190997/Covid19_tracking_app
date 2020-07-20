package com.northpolewebservice.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class CountryDetailActivity extends AppCompatActivity {


    private TextView tvCountry, tvCases, tvActive, tvRecovered, tvCritical, tvTodayCases, tvTodayDeaths, tvTotalDeaths;
    private int positionCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        tvCountry = findViewById(R.id.tvCounty);
        tvCases = findViewById(R.id.tvCases);
        tvActive = findViewById(R.id.tvActive);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTotalDeaths = findViewById(R.id.tvTotalDeath);


        tvCountry.setText(CountriesAffected.modelContriesList.get(positionCountry).getCountry());
        tvCases.setText(CountriesAffected.modelContriesList.get(positionCountry).getCases());
        tvActive.setText(CountriesAffected.modelContriesList.get(positionCountry).getActive());
        tvRecovered.setText(CountriesAffected.modelContriesList.get(positionCountry).getRecovered());
        tvCritical.setText(CountriesAffected.modelContriesList.get(positionCountry).getCritical());
        tvTodayCases.setText(CountriesAffected.modelContriesList.get(positionCountry).getTodayCases());
        tvTodayDeaths.setText(CountriesAffected.modelContriesList.get(positionCountry).getTodayDeaths());
        tvTotalDeaths.setText(CountriesAffected.modelContriesList.get(positionCountry).getDeaths());



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
