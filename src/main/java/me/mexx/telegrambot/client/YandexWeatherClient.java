package me.mexx.telegrambot.client;

import me.mexx.telegrambot.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class YandexWeatherClient {

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${yandex.weather.json.url}")
    private String urlYandex;

    @Value("${yandex.api.key}")
    private String KeyForYandexWeather;

    public String getJsonFromUrl() throws ServiceException {

        Request request = new Request.Builder().url(urlYandex)
                .header("X-Yandex-API-Key", KeyForYandexWeather).build();

        try (Response response = okHttpClient.newCall(request).execute()) {

            ResponseBody body = response.body();

            return body == null ? null : body.string();


        } catch (IOException e) {
            throw new ServiceException("Ошибка c получением погоды", e);
        }


    }


}
