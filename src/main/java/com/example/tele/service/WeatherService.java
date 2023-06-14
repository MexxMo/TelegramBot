package com.example.tele.service;

import com.example.tele.exception.ServiceException;

import java.io.IOException;

public interface WeatherService {
    String getWeather() throws ServiceException;
}
