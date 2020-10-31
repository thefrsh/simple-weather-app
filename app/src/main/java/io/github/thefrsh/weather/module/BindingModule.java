package io.github.thefrsh.weather.module;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.github.thefrsh.weather.service.WeatherWebService;
import io.github.thefrsh.weather.service.WeatherWebServiceImpl;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class BindingModule
{
    @Binds
    public abstract WeatherWebService weatherWebService(WeatherWebServiceImpl weatherWebService);
}
