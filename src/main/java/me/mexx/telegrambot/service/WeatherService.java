package me.mexx.telegrambot.service;

import me.mexx.telegrambot.exception.ServiceException;

public interface WeatherService {
    String getWeather(String city) throws ServiceException;
}
