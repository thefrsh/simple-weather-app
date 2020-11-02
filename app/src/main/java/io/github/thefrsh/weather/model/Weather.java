package io.github.thefrsh.weather.model;

import android.app.Service;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Parcelable
{
    private String main;
    private String description;
    private Double temperature;
    private Double feelsLikeTemperature;
    private Integer pressure;
    private Double windSpeed;

    protected Weather(Parcel in)
    {
        main = in.readString();
        description = in.readString();
        if (in.readByte() == 0)
        {
            temperature = null;
        }
        else
        {
            temperature = in.readDouble();
        }
        if (in.readByte() == 0)
        {
            feelsLikeTemperature = null;
        }
        else
        {
            feelsLikeTemperature = in.readDouble();
        }
        if (in.readByte() == 0)
        {
            pressure = null;
        }
        else
        {
            pressure = in.readInt();
        }
        if (in.readByte() == 0)
        {
            windSpeed = null;
        }
        else
        {
            windSpeed = in.readDouble();
        }
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>()
    {
        @Override
        public Weather createFromParcel(Parcel in)
        {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size)
        {
            return new Weather[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(main);
        dest.writeString(description);

        if (temperature == null)
        {
            dest.writeByte((byte) 0);
        }
        else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(temperature);
        }
        if (feelsLikeTemperature == null)
        {
            dest.writeByte((byte) 0);
        }
        else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(feelsLikeTemperature);
        }
        if (pressure == null)
        {
            dest.writeByte((byte) 0);
        }
        else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(pressure);
        }
        if (windSpeed == null)
        {
            dest.writeByte((byte) 0);
        }
        else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(windSpeed);
        }
    }
}
