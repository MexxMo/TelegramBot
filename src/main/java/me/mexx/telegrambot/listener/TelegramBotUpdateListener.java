package me.mexx.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mexx.telegrambot.constant.Information;
import me.mexx.telegrambot.exception.ServiceException;
import me.mexx.telegrambot.replymarkup.ReplyMarkup;
import me.mexx.telegrambot.service.ExchangeRatesService;
import me.mexx.telegrambot.service.WeatherService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static me.mexx.telegrambot.constant.Keyboard.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotUpdateListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final ExchangeRatesService exchangeRatesService;
    private final WeatherService weatherService;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        ReplyMarkup replyMarkup = new ReplyMarkup(telegramBot);
        try {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        log.info("упдата {}", update);
                        Message message = update.message();
                        Long chatId = message.chat().id();
//                        Chat chat = message.chat();
                        String text = message.text();
                        String userName = message.chat().username();
                        log.info("текст {} ", text);
                        log.info("сообщение {} ", message);

                        switch (text) {
                            case START -> replyMarkup.sendStartMenu(chatId, userName);


                            case USD, "/usd" -> usdCommand(chatId);

                            case EUR, "/eur" -> eurCommand(chatId);

                            case CNY, "/cny" -> cnyCommand(chatId);

                            case HELP, "/help" -> helpCommand(chatId);
                            case "/weather", WEATHER -> weatherCommand(chatId);

                            default -> unknownCommand(chatId);

                        }


                    });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(Long chatId, String message) {
        SendResponse sendResponse = telegramBot.execute(new SendMessage(chatId, message));
        if (!sendResponse.isOk()) {
            log.error(sendResponse.description());
        }

    }

    private void weatherCommand(Long chatId) {
        String weather;
        try {
            weather = weatherService.getWeather();
        } catch (ServiceException e) {
            e.printStackTrace();
            weather = "Что-то пошло не так";
        }
        sendMessage(chatId, weather);
    }

    private void usdCommand(Long chatId) {
        String formattedText;
        try {
            String usd = exchangeRatesService.getUSD();
            String text = "Курс доллара в ЦБ на %s составляет %s рублей";
            formattedText = String.format(text, LocalDate.now(), usd);
        } catch (ServiceException e) {
            log.error("Какая-то срань с долларом", e);
            formattedText = "Доллар ебнулся. Попробуй позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            String usd = exchangeRatesService.getEUR();
            String text = "Курс евро в ЦБ на %s составляет %s рублей";
            formattedText = String.format(text, LocalDate.now(), usd);
        } catch (ServiceException e) {
            log.error("Какая-то срань с евро", e);
            formattedText = "Евро ебнулся. Попробуй позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void cnyCommand(Long chatId) {
        String formattedText;
        try {
            String usd = exchangeRatesService.getCNY();
            String text = "Курс китайского юаня в ЦБ на %s составляет %s рублей";
            formattedText = String.format(text, LocalDate.now(), usd);
        } catch (ServiceException e) {
            log.error("Какая-то срань с Китаем", e);
            formattedText = "Китай ебнулся. Попробуй позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void helpCommand(Long chatId) {
        String text = Information.HELP_INFO;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        String text = "Че пальцы кривые? На кнопки тыкай";
        sendMessage(chatId, text);
    }

}
