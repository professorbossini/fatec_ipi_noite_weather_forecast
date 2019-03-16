package br.com.bossini.fatec_ipi_noite_weather_forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WeatherArrayAdapter
        extends ArrayAdapter <Weather> {
    WeatherArrayAdapter(Context context, List<Weather> previsoes){
        super(context, -1, previsoes);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        LayoutInflater inflater =
                LayoutInflater.from(getContext());
        View caixa = 
                inflater.
                        inflate(R.layout.list_item,
                        parent, false);
        Weather caraDaVez = getItem(position);
        TextView dayTextView =
                caixa.findViewById(R.id.dayTextView);
        TextView lowTextView =
                caixa.findViewById(R.id.lowTextView);
        TextView highTextView =
                caixa.findViewById(R.id.highTextView);
        TextView humidityTextView =
                caixa.findViewById(R.id.humidityTextView);
        dayTextView.setText(getContext().
                getString(R.string.day_description,
                        caraDaVez.dayOfWeek,
                        caraDaVez.description));
        lowTextView.setText(
                getContext().
                    getString(
                            R.string.low_temp,
                            caraDaVez.minTemp
                    )
        );
        highTextView.setText(
                getContext().
                        getString(
                                R.string.high_temp,
                                caraDaVez.maxTemp
                        )
        );
        humidityTextView.setText(
                getContext().
                        getString(
                             R.string.humidity,
                             caraDaVez.humidity
                        )
        );
        return caixa;
    }
}
