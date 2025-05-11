package com.example.trade_system.commands;

public class CancelOrderCommand implements Command {
    private final String commandId;
    private final long timestamp;
    private final String orderId;

    public CancelOrderCommand(String commandId, long timestamp, String orderId) {
        this.commandId = commandId;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }

    public String getCommandId() { return commandId; }
    public long getTimestamp() { return timestamp; }
    public String getOrderId() { return orderId; }
}
