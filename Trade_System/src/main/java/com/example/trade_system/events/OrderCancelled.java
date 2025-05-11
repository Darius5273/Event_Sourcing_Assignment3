package com.example.trade_system.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCancelled implements Event {
    private final String eventId;
    private final long timestamp;
    private final String orderId;

    @JsonCreator
    public OrderCancelled(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("orderId") String orderId
    ) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }

    public String getEventId() { return eventId; }
    public long getTimestamp() { return timestamp; }
    public String getOrderId() { return orderId; }
}
