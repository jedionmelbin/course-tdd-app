package com.xprotec.app.clients;

import com.xprotec.app.type.AppHttpStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ExchangeClientProcessor implements IExchangeClientProcessor {

    private final WebClient webClient;

    @Override
    public ResponseEntity<Object> getExchange(String symbols) {

        try {
            ResponseEntity<Object> responseEntity = webClient.get()
                    .uri("http://localhost:8060/fixer/latest", uriBuilder -> uriBuilder
                            .queryParam("base", "USD")
                            .queryParam("symbols", symbols)
                            .build())
                    .headers(httpHeaders -> httpHeaders.setAll(setHeaders()))
                    .retrieve()
                    .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class)
                            .map(Exception::new))
                    .onStatus(httpStatus -> httpStatus.value() == AppHttpStatusCode.FUNCTIONAL_ERROR.getCode(),
                            clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                    .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse ->
                            clientResponse.bodyToMono(String.class).map(Exception::new))
                    .toEntity(Object.class)
                    .timeout(Duration.ofSeconds(2000L))
                    .block();

            return responseEntity;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Map<String, String> setHeaders() {
        Map<String, String> headers = new HashMap<>();
        return headers;
    }
}
