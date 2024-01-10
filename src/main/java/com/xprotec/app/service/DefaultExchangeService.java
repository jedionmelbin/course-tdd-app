package com.xprotec.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xprotec.app.clients.IExchangeClientProcessor;
import com.xprotec.app.service.dto.response.ExchangeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultExchangeService implements ExchangeService {

    private final IExchangeClientProcessor exchangeClientProcessor;

    private final ObjectMapper objectMapper;

    @Override
    public ExchangeResponseDto getExchange(String symbols) {
        ResponseEntity<Object> responseEntity = exchangeClientProcessor.getExchange(symbols);
        ExchangeResponseDto responseDto = objectMapper.convertValue(responseEntity.getBody(), ExchangeResponseDto.class);
        if (responseDto != null) {
            log.trace("This object not empty");
        }
        return responseDto;
    }
}
