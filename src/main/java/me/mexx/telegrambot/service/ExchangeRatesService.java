package me.mexx.telegrambot.service;

import me.mexx.telegrambot.exception.ServiceException;

public interface ExchangeRatesService {

    String getUSD() throws ServiceException;
    String getEUR() throws ServiceException;
    String getCNY() throws ServiceException;

}
