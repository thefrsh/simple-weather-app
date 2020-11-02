package io.github.thefrsh.weather.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.inject.Inject;

import io.github.thefrsh.weather.model.Weather;
import io.github.thefrsh.weather.troubleshooting.CityNotFoundException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        String url = BASE_URL + "?q=" + city + "&units=metric" + "&APPID=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Weather.WeatherBuilder weatherBuilder = Weather.builder();

        try (Response response = httpClient.newCall(request).execute())
        {
            if (response.isSuccessful() && response.body() != null)
            {
                JSONParser jsonParser = new JSONParser();

                JSONObject jsonBody = (JSONObject) jsonParser.parse(response.body().string());

                JSONArray weatherArray = jsonBody.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);

                weatherBuilder.main(weatherObject.getString("main"));
                weatherBuilder.description(weatherObject.getString("description"));

                JSONObject mainObject = jsonBody.getJSONObject("main");

                weatherBuilder.temperature(mainObject.getDouble("temp"));
                weatherBuilder.feelsLikeTemperature(mainObject.getDouble("feels_like"));
                weatherBuilder.pressure(mainObject.getInt("pressure"));

                JSONObject windObject = jsonBody.getJSONObject("wind");
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
