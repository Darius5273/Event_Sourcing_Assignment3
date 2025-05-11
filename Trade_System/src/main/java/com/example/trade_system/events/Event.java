package com.example.trade_system.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderPlaced.class, name = "OrderPlaced"),
        @JsonSubTypes.Type(value = OrderCancelled.class, name = "OrderCancelled"),
        @JsonSubTypes.Type(value = TradeExecuted.class, name = "TradeExecuted"),
        @JsonSubTypes.Type(value = FundsDebited.class, name = "FundsDebited"),
        @JsonSubTypes.Type(value = FundsCredited.class, name = "FundsCredited")
})
public interface Event {
    String getEventId();
    long getTimestamp();
}
