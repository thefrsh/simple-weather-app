package io.github.thefrsh.weather.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.github.thefrsh.weather.R;
import io.github.thefrsh.weather.databinding.ActivityMainBinding;
import io.github.thefrsh.weather.dialog.QuitDialog;
import io.github.thefrsh.weather.rx.RxEventType;
import io.github.thefrsh.weather.viewmodel.MainViewModel;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
{
    private static final String TYPED_TEXT = "typed_text";
    private static final String CITIES_LIST_FILENAME = "CitiesList.txt";

    private ActivityMainBinding binding;

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel.setCitiesList(loadCitiesListFromAsset());

        binding.setMainViewModel(mainViewModel);

        Disposable disposable = mainViewModel.getEvents().subscribe(event ->
        {
            if (event == RxEventType.CITY_NOT_FOUND)
            {
                Toast.makeText(this, "Please provide correct city name",
                        Toast.LENGTH_LONG).show();
            }
            else if (event == RxEventType.QUIT_ATTEMPT)
            {
                QuitDialog quitDialog = new QuitDialog();
                quitDialog.show(getSupportFragmentManager(), "QuitDialog");
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(TYPED_TEXT, mainViewModel.getCity());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mainViewModel.setCity(savedInstanceState.getString(TYPED_TEXT));
    }

    private List<String> loadCitiesListFromAsset()
    {
        AssetManager assetManager = getAssets();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                assetManager.open(CITIES_LIST_FILENAME), StandardCharsets.UTF_8)))
        {
            return reader.lines().collect(Collectors.toList());
        }
        catch (IOException e)
        {
            finish();
        }

        return null;
    }

}