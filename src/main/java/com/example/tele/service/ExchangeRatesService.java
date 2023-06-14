package com.example.tele.service;

import com.example.tele.exception.ServiceException;

public interface ExchangeRatesService {

    String getUSD() throws ServiceException;
    String getEUR() throws ServiceException;
    String getCNY() throws ServiceException;

}
