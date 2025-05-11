package com.example.trade_system.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderPlaced implements Event {
    private final String eventId;
    private final long timestamp;
    private final String orderId;
    private final String userId;
    private final String side;
    private final String price;
    private final int quantity;

    @JsonCreator
    public OrderPlaced(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("orderId") String orderId,
            @JsonProperty("userId") String userId,
            @JsonProperty("side") String side,
            @JsonProperty("price") String price,
            @JsonProperty("quantity") int quantity
    ) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.userId = userId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public String getEventId() { return eventId; }
    public long getTimestamp() { return timestamp; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getSide() { return side; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
