package com.xprotec.app.service;

import com.xprotec.app.service.dto.response.ExchangeResponseDto;
public interface ExchangeService {
    ExchangeResponseDto getExchange(String symbols);
}
