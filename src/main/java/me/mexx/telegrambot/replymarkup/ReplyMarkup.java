package me.mexx.telegrambot.replymarkup;

import me.mexx.telegrambot.constant.Information;
import me.mexx.telegrambot.constant.Keyboard;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplyMarkup {

    private final TelegramBot telegramBot;

    public void sendStartMenu(long chatId, String userName) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Keyboard.START),
                new KeyboardButton(Keyboard.HELP));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Keyboard.USD),
        new KeyboardButton(Keyboard.EUR),
        new KeyboardButton(Keyboard.CNY));
        replyKeyboardMarkup.addRow(new KeyboardButton(Keyboard.WEATHER
        ));
        String text = Information.START;
        String formattedText = String.format(text, userName);

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, formattedText);
    }


    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        telegramBot.execute(request);
    }

}
