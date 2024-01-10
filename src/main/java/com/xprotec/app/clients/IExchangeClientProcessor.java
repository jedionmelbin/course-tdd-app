package com.xprotec.app.clients;

import org.springframework.http.ResponseEntity;

public interface IExchangeClientProcessor {
    ResponseEntity<Object> getExchange(String symbols);
}
