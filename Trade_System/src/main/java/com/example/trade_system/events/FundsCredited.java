package com.example.trade_system.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FundsCredited implements Event {
    private final String eventId;
    private final long timestamp;
    private final String userId;
    private final String amount;

    @JsonCreator
    public FundsCredited(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("userId") String userId,
            @JsonProperty("amount") String amount
    ) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.amount = amount;
    }
    public String getEventId() { return eventId; }
    public long getTimestamp() { return timestamp; }
    public String getUserId() { return userId; }
    public String getAmount() { return amount; }
}
