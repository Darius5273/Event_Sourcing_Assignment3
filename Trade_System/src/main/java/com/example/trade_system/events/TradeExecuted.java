package com.example.trade_system.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeExecuted implements Event {
    private final String eventId;
    private final long timestamp;
    private final String buyOrderId;
    private final String sellOrderId;
    private final String price;
    private final int quantity;

    @JsonCreator
    public TradeExecuted(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("buyOrderId") String buyOrderId,
            @JsonProperty("sellOrderId") String sellOrderId,
            @JsonProperty("price") String price,
            @JsonProperty("quantity") int quantity
    ) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getEventId() { return eventId; }
    public long getTimestamp() { return timestamp; }
    public String getBuyOrderId() { return buyOrderId; }
    public String getSellOrderId() { return sellOrderId; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
