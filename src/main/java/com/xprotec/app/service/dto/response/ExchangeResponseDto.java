package com.xprotec.app.service.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Map;

@Builder
@Data
public class ExchangeResponseDto {
    private boolean success;
    private int timestamp;
    private String base;
    private String date;
    private Map<String, Object> rates;
    //public Rates rates;
    /*public static class Rates {
    }*/
}
