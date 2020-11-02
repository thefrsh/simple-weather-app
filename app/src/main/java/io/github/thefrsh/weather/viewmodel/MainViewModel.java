package io.github.thefrsh.weather.viewmodel;

import android.view.View;

import java.util.List;

public interface MainViewModel
{
    void onCheckWeatherButtonClick(View view);

    String getCity();

    void setCity(String city);

    void setCitiesList(List<String> citiesList);

    List<String> getCitiesList();
}
