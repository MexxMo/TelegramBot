package me.mexx.telegrambot.config;

import com.pengrad.telegrambot.TelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {
    @Bean
    public TelegramBot telegramBot(@Value("${telegram.bot.token}") String token){
        return new TelegramBot(token);
    }
    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

}
