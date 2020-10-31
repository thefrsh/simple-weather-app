package io.github.thefrsh.weather.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.github.thefrsh.weather.model.Weather;
import io.github.thefrsh.weather.troubleshooting.CityNotFoundException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@AndroidEntryPoint
public class WeatherWebServiceImpl implements WeatherWebService
{
    private static final String API_KEY = "f042b204d98c95586a6c4987242f2717";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final OkHttpClient httpClient;

    @Inject
    public WeatherWebServiceImpl(OkHttpClient client)
    {
        this.httpClient = client;
    }

    @Override
    public Weather getWeatherByCityName(String city) throws IOException
    {
        if (city == null)
        {
            throw new IllegalArgumentException("City name is null");
        }

        var url = BASE_URL + "?q=" + city + "&units=metric" + "&APPID=" + API_KEY;

        var request = new Request.Builder()
                .url(url)
                .get()
                .build();

        var weatherBuilder = Weather.builder();

        try (var response = httpClient.newCall(request).execute())
        {
            if (response.isSuccessful() && response.body() != null)
            {
                var jsonParser = new JSONParser();

                var jsonBody = (JSONObject) jsonParser.parse(response.body().string());

                var weatherArray = jsonBody.getJSONArray("weather");
                var weatherObject = weatherArray.getJSONObject(0);

                weatherBuilder.main(weatherObject.getString("main"));
                weatherBuilder.description(weatherObject.getString("description"));

                var mainObject = jsonBody.getJSONObject("main");

                weatherBuilder.temperature(mainObject.getDouble("temp"));
                weatherBuilder.feelsLikeTemperature(mainObject.getDouble("feels_like"));
                weatherBuilder.pressure(mainObject.getInt("pressure"));

                var windObject = jsonBody.getJSONObject("wind");
                weatherBuilder.windSpeed(windObject.getDouble("speed"));
            }
            else
            {
                throw new CityNotFoundException();
            }
        }
        catch (ParseException | JSONException e)
        {
            throw new IOException(e);
        }

        return weatherBuilder.build();
    }
}
