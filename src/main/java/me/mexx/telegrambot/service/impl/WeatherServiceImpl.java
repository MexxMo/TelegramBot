package me.mexx.telegrambot.service.impl;


import me.mexx.telegrambot.exception.ServiceException;
import me.mexx.telegrambot.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Value("${yandex.weather.json.url}")
    private String urlYandex;
    @Value("${yandex.api.key}")
    private String getKey;

    @Autowired
    private OkHttpClient client;
    @Autowired
    private Gson gson;


    @Override
    public String getWeather() throws ServiceException {

        String json = getJsonFromUrl(urlYandex);

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


    private String getJsonFromUrl(String url) throws ServiceException {

        Request request = new Request.Builder().url(url).header("X-Yandex-API-Key", getKey).build();

        try (Response response = client.newCall(request).execute()) {

            ResponseBody body = response.body();

            return body == null ? null : body.string();


        } catch (IOException e) {
            throw new ServiceException("Ошибка c получением погоды", e);
        }


    }
}
