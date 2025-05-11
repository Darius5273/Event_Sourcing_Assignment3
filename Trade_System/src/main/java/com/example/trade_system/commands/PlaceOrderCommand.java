package com.example.trade_system.commands;

public class PlaceOrderCommand implements Command {
    private final String commandId;
    private final long timestamp;
    private final String orderId;
    private final String userId;
    private final String side;
    private final String price;
    private final int quantity;

    public PlaceOrderCommand(String commandId, long timestamp, String orderId, String userId, String side, String price, int quantity) {
        this.commandId = commandId;
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.userId = userId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCommandId() { return commandId; }
    public long getTimestamp() { return timestamp; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getSide() { return side; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
