package com.flashsale.api.controller;

import com.flashsale.api.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flashsale.api.kafka.PurchaseEventPublisher;


//Handles purchase limit during flash sale

@RestController
public class PurchaseController {

    private final StockService stockService;
    private final PurchaseEventPublisher eventPublisher;
    // Constructor injection ensures the dependency is explicit and immutable
    public PurchaseController(StockService stockService, PurchaseEventPublisher eventPublisher) {
        this.stockService = stockService;
        this.eventPublisher=eventPublisher;
    }

    /**
     * Attempts to buy a single item for a user.
     * Stock validation and decrement happen atomically in Redis (Lua script).
     *
     * @param userId identifier of the buyer (used later for events)
     * @param itemId identifier of the item being purchased
     */
    @PostMapping("/buy")
    public ResponseEntity<String> buy(
            @RequestParam String userId,
            @RequestParam String itemId) {

        boolean success = stockService.tryBuy(itemId);

        // 200 OK when purchase succeeds
        if (success) {
            eventPublisher.publish(userId, itemId);
            return ResponseEntity.ok("Purchase successful");
        }

        // 409 CONFLICT clearly communicates "no stock left"
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Item sold out");
    }
}
