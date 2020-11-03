package io.github.thefrsh.weather.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import java.util.List;

import javax.inject.Inject;

import io.github.thefrsh.weather.BR;
import io.github.thefrsh.weather.activity.WeatherActivity;
import io.github.thefrsh.weather.model.Weather;
import io.github.thefrsh.weather.rx.RxEventType;
import io.github.thefrsh.weather.service.WeatherWebService;
import io.github.thefrsh.weather.troubleshooting.CityNotFoundException;
import io.reactivex.subjects.PublishSubject;

public class MainViewModelImpl extends BaseObservable implements MainViewModel
{
    private static final String EMPTY_STRING = "";
    private static final String WEATHER = "weather";
    private static final String CITY_NAME = "city_name";

    private final WeatherWebService weatherWebService;
    private final PublishSubject<RxEventType> publishSubject;

    private String city;
    private List<String> citiesList;

    @Inject
    public MainViewModelImpl(WeatherWebService weatherWebService)
    {
        this.weatherWebService = weatherWebService;
        this.publishSubject = PublishSubject.create();
    }

    @Override
    public void onCheckWeatherButtonClick(View view)
    {
        Context context = view.getContext();

        try
        {
            if (city == null || city.equals(EMPTY_STRING) || !citiesList.contains(city))
            {
                throw new CityNotFoundException();
            }
            else
            {
                Weather weather = weatherWebService.getWeatherByCityName(city);

                Intent intent = new Intent(context, WeatherActivity.class);

                intent.putExtra(WEATHER, weather);
                intent.putExtra(CITY_NAME, city);

                ((Activity)(context)).startActivity(intent);
                ((Activity)(context)).finish();
            }
        }
        catch (CityNotFoundException e)
        {
            this.publishSubject.onNext(RxEventType.CITY_NOT_FOUND);
        }
    }

    @Override
    public void onQuitButtonClick(View view)
    {
        publishSubject.onNext(RxEventType.QUIT_ATTEMPT);
    }

    @Override
    public PublishSubject<RxEventType> getEvents()
    {
        return this.publishSubject;
    }

    @Override
    public void setCitiesList(List<String> citiesList)
    {
        this.citiesList = citiesList;
    }

    @Override
    public List<String> getCitiesList()
    {
        return this.citiesList;
    }

    @BindingAdapter("android:cities")
    public static void setCities(AutoCompleteTextView autoCompleteTextView, List<String> citiesList)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(autoCompleteTextView.getContext(),
                android.R.layout.simple_dropdown_item_1line, citiesList);

        autoCompleteTextView.setAdapter(adapter);
    }

    @Bindable
    @Override
    public String getCity()
    {
        return this.city;
    }

    @Override
    public void setCity(String city)
    {
        this.city = city;
        notifyPropertyChanged(BR.city);
    }
}
