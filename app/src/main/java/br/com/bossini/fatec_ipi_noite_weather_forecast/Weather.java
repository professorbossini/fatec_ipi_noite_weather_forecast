package br.com.bossini.fatec_ipi_noite_weather_forecast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Weather {
    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconURL;

    public Weather(long timeStamp, double minTemp, double maxTemp,
                   int humidity, String description,
                                String iconName){
        NumberFormat nf =
                NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        NumberFormat nfPercent =
                NumberFormat.getPercentInstance();
        this.minTemp = nf.format(minTemp);
        this.maxTemp = nf.format(maxTemp);
        this.humidity = nfPercent.format(humidity / 100.0);
        this.description = description;
        this.iconURL = "http://openweathermap.org/img/w/" + iconName +
                ".png";
        this.dayOfWeek = converterTimeStampToDay (timeStamp);
    }
    public String converterTimeStampToDay (long timeStamp){
        Calendar agora = Calendar.getInstance();
        agora.setTimeInMillis(timeStamp * 1000);
        TimeZone tz = TimeZone.getDefault();
        agora.add(Calendar.MILLISECOND, tz.getOffset(agora.getTimeInMillis()));
        return new SimpleDateFormat("EEE, HH:mm").format(agora.getTime());
    }
}
