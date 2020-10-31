package io.github.thefrsh.weather.module;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn(ApplicationComponent.class)
public class ProvidingModule
{
    @Provides
    public OkHttpClient okHttpClient()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }
}
