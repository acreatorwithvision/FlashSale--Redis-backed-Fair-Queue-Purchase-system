package com.flashsale.api.controller;

import com.flashsale.api.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flashsale.api.service.StockService.*;

@RestController
public class HealthController {
    

    @GetMapping("/health")
    public String health(){
        return "OK";
    }

    private final StockService stockService;
    //Constructor injection
    public HealthController(StockService stockService){
        this.stockService=stockService;
    }

    @GetMapping("/test-buy")
    public String testBuy(){
        return stockService.tryBuy("iphone15") ? "SUCCESS" : "SOLD OUT";
    }
}
