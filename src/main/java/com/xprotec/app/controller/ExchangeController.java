package com.xprotec.app.controller;

import com.xprotec.app.service.ExchangeService;
import com.xprotec.app.service.dto.response.ExchangeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping()
    public ResponseEntity<ExchangeResponseDto> getExchange(@RequestParam("symbols")String symbols) {
        return ResponseEntity.ok(exchangeService.getExchange(symbols));
    }
}
