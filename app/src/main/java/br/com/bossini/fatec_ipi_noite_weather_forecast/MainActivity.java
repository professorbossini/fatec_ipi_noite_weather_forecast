package br.com.bossini.fatec_ipi_noite_weather_forecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private List<Weather> previsoes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationEditText = findViewById(R.id.locationEditText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((v)->{
            String cidade = locationEditText.
                    getEditableText().toString();
            String endereco =
                    getString(
                            R.string.web_service_url,
                            cidade,
                            getString(R.string.api_key),
                            getString(R.string.measurement_unit));

            new GetWeatherTask().execute(endereco);
        });
    }

    private class GetWeatherTask
                    extends AsyncTask <String, Void, String>{
        @Override
        protected String doInBackground(String... enderecos){
            String endereco = enderecos[0];
            try {
                URL url = new URL(endereco);
                HttpURLConnection conn =
                        (HttpURLConnection)url.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader =
                        new BufferedReader(isr);
                String linha = null;
                StringBuilder resultado = new StringBuilder ("");
                while ((linha = reader.readLine()) != null){
                    resultado.append(linha);
                }
                JSONObject jsonInteiro =
                        new JSONObject(resultado.toString());
                JSONArray list =
                        jsonInteiro.getJSONArray("list");
                for (int i = 0; i < list.length(); i++){
                    JSONObject iesimo =
                            list.getJSONObject(i);
                    long dt =
                            iesimo.getLong("dt");
                    JSONObject main =
                            iesimo.getJSONObject("main");
                    double temp_min =
                            main.getDouble("temp_min");
                    double temp_max =
                            main.getDouble("temp_max");
                    int humidity =
                            main.getInt("humidity");
                    JSONArray weather =
                            iesimo.getJSONArray("weather");
                    JSONObject unicoDoWeather =
                            weather.getJSONObject(0);
                    String description =
                            unicoDoWeather.getString("description");
                    String icon =
                            unicoDoWeather.getString("icon");
                    Weather w =
                            new Weather (dt, temp_min, temp_max,
                                    humidity, description, icon);
                    previsoes.add(w);
                }
                return resultado.toString();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this,
                    s, Toast.LENGTH_SHORT).show();

        }
    }




}
