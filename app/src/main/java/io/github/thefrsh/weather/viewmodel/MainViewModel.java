package io.github.thefrsh.weather.viewmodel;

import android.view.View;

import java.util.List;

import io.github.thefrsh.weather.rx.RxEventType;
import io.reactivex.subjects.PublishSubject;

public interface MainViewModel
{
    void onCheckWeatherButtonClick(View view);

    String getCity();

    void setCity(String city);

    void setCitiesList(List<String> citiesList);

    List<String> getCitiesList();

    void onQuitButtonClick(View view);

    PublishSubject<RxEventType> getEvents();
}
