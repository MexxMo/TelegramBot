package me.mexx.telegrambot.replymarkup;

import com.pengrad.telegrambot.model.Update;
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


    public void sendStartMenu(long chatId, String userName, Update update) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
            new KeyboardButton(Keyboard.START),
            new KeyboardButton(Keyboard.HELP));
    replyKeyboardMarkup.addRow(
            new KeyboardButton(Keyboard.USD),
            new KeyboardButton(Keyboard.EUR),
            new KeyboardButton(Keyboard.CNY));
    replyKeyboardMarkup.addRow(new KeyboardButton(Keyboard.WEATHER));

    String text;
    String buttonText = update.message().text();

    if (buttonText.equals(Keyboard.START)) {
        text = String.format(Information.START, userName);
    } else {
        text = "Главное меню";
    }


    returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, text);
}


    public void weatherMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Москва"),
                new KeyboardButton("Челябинск"),
                new KeyboardButton("п.Западный"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Главное меню"));
                returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Выбери город/нас. пункт");
    }




    private void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
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
