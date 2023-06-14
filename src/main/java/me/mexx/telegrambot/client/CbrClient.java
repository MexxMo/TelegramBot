package me.mexx.telegrambot.client;

//@Component
//public class CbrClient {
//    @Autowired
//    private OkHttpClient client;
//
//    @Value("${cbr.currency.rates.xml.url}")
//    private String urlCbr;
//
//    public String getCurrencyRatesXML() throws ServiceException {
//
//        Request request = new Request.Builder().url(urlCbr).build();
//
//        try (Response response = client.newCall(request).execute()) {
//            ResponseBody body = response.body();
//            return body == null ? null : body.string();
//        } catch (IOException e) {
//            throw new ServiceException("Ошибка получения курса валют", e);
//        }
//    }
//
//}
