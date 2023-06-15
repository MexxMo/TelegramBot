package me.mexx.telegrambot.service.impl;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.mexx.telegrambot.client.YandexWeatherClient;
import me.mexx.telegrambot.exception.ServiceException;
import me.mexx.telegrambot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private YandexWeatherClient yandexWeatherClient;
    @Autowired
    private Gson gson;


    @Override
    public String getWeather() throws ServiceException {

        String json = yandexWeatherClient.getJsonFromUrl();

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        String temp = jsonObject.get("fact").getAsJsonObject().get("temp").getAsString();

        String feels_like = jsonObject.get("fact").getAsJsonObject().get("feels_like").getAsString();

        String pressure_mm = jsonObject.get("fact").getAsJsonObject().get("pressure_mm").getAsString();

        String geo_lat = jsonObject.get("geo_object").getAsJsonObject().get("locality")
                .getAsJsonObject().get("name").getAsString();

        String urlYa = jsonObject.get("info").getAsJsonObject().get("url").getAsString();

        System.out.println(jsonObject);

        return "Населенный пункт: " + geo_lat + "\n" +
                "Температура: " + temp + "°C \n" +
                "Ощущаемая температура: " + feels_like + "°C \n" +
                "Давление: " + pressure_mm + " в мм рт. ст. \n" +
                "Ссылка на подробный прогноз погоды: " + urlYa;

    }


}
