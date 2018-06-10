package com.maxime.monappmeteo;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    URL urlWheather;
    private ListView listMeteo;
    private List<Meteo> meteo = new ArrayList<>();
    private AdapterMeteo adapter;
    private TextView villeMain;
    boolean detail = false;
    private int choice;
    private Button btnActu;
    private EditText etVille;
    private String villeChoisie;
    private String meteoForcast;
    private Button btnOK;
    private TextView tvDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMeteo = findViewById(R.id.listViewMeteo);
        choice=1;
        villeMain = findViewById(R.id.tvVilleMain);
        btnActu = findViewById(R.id.btnAsynch);
        etVille = findViewById(R.id.etVille);
        btnOK = findViewById(R.id.btnOK);
        tvDetail = findViewById(R.id.tvDetail);
        etVille.setText("Lyon");

        tvDetail.setVisibility(View.INVISIBLE);

        meteoForcast = "http://api.openweathermap.org/data/2.5/forecast?q=Lyon,fr&units=metrics&APPID=88cb3a32b8e67c872b6161a0ec8fb13a";

        try {
            urlWheather = new URL(meteoForcast);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        adapter = new AdapterMeteo(MainActivity.this, meteo);
        listMeteo.setAdapter(adapter);

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Toast.makeText(MainActivity.this, "Pas de connexion détéctée", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Connecté", Toast.LENGTH_SHORT).show();
            detail = false;

            listMeteo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    detail = true;
                    new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, position);
                    choice=2;
                    btnActu.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    etVille.setVisibility(View.INVISIBLE);
                    tvDetail.setVisibility(View.VISIBLE);
                }
            });

            new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, -1);

        }

    }

    public void recupData(View view){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Toast.makeText(MainActivity.this, "Pas de connexion détécté", Toast.LENGTH_SHORT).show();
        }
        else{
            detail = false;

            listMeteo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    detail = true;
                    new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, position);
                    choice=2;
                    btnActu.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    etVille.setVisibility(View.INVISIBLE);
                    tvDetail.setVisibility(View.VISIBLE);
                }
            });

            new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, -1);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Toast.makeText(MainActivity.this, "Pas de connexion détécté", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Connecté", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(choice==2){
            new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, -1);
            choice=1;
            btnActu.setVisibility(View.VISIBLE);
            btnOK.setVisibility(View.VISIBLE);
            etVille.setVisibility(View.VISIBLE);
            tvDetail.setVisibility(View.INVISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    public void newVille(View view){
        villeChoisie = etVille.getText().toString();
        if (villeChoisie.equals("")){
            meteoForcast = "http://api.openweathermap.org/data/2.5/forecast?q=Lyon,fr&units=metrics&APPID=88cb3a32b8e67c872b6161a0ec8fb13a";
        } else {
            meteoForcast = "http://api.openweathermap.org/data/2.5/forecast?q=" + villeChoisie + ",fr&units=metrics&APPID=88cb3a32b8e67c872b6161a0ec8fb13a";
        }

        try {
            urlWheather = new URL(meteoForcast);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Toast.makeText(MainActivity.this, "Pas de connexion détécté", Toast.LENGTH_SHORT).show();
        }
        else{
            detail = false;

            listMeteo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    detail = true;
                    new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, position);
                    choice=2;
                    btnActu.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    etVille.setVisibility(View.INVISIBLE);
                    tvDetail.setVisibility(View.VISIBLE);
                }
            });

            new MyAsynchTask(MainActivity.this).execute(urlWheather,adapter,meteo,villeMain, -1);

        }

    }
}
