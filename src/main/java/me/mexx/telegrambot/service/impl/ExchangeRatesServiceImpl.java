package me.mexx.telegrambot.service.impl;

//import com.example.tele.client.CbrClient;

import me.mexx.telegrambot.exception.ServiceException;
import me.mexx.telegrambot.service.ExchangeRatesService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    @Autowired
    private OkHttpClient client;

    @Value("${cbr.currency.rates.xml.url}")
    private String urlCbr;

    private final static String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private final static String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    private final static String CNY_XPATH = "/ValCurs//Valute[@ID='R01375']/Value";



    @Override
    public String getUSD() throws ServiceException {
        String xml = getCurrencyRatesXML();

        return extractCurrencyValueFromXml(xml, USD_XPATH);
    }

    @Override
    public String getEUR() throws ServiceException {
        String xml = getCurrencyRatesXML();

        return extractCurrencyValueFromXml(xml, EUR_XPATH);
    }

    @Override
    public String getCNY() throws ServiceException {
        String xml = getCurrencyRatesXML();

        return extractCurrencyValueFromXml(xml, CNY_XPATH);
    }





    private String getCurrencyRatesXML() throws ServiceException {

        Request request = new Request.Builder().url(urlCbr).build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new ServiceException("Ошибка получения курса валют", e);
        }
    }

    private static String extractCurrencyValueFromXml(String xml, String xpathExpression) throws ServiceException {
        InputSource source = new InputSource(new StringReader(xml));
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            Document document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Проблема с XML", e);
        }
    }

}
