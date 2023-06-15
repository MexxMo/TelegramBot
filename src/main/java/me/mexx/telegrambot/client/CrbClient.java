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
public class CrbClient {

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${cbr.currency.rates.xml.url}")
    private String urlCbr;


    public String getCurrencyRatesXML() throws ServiceException {

        Request request = new Request.Builder().url(urlCbr).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new ServiceException("Ошибка получения курса валют", e);
        }
    }


}
