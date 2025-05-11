package com.example.trade_system.commands;

public class ExecuteTradeCommand implements Command {
    private final String commandId;
    private final long timestamp;
    private final String buyOrderId;
    private final String sellOrderId;
    private final String price;
    private final int quantity;

    public ExecuteTradeCommand(String commandId, long timestamp, String buyOrderId, String sellOrderId, String price, int quantity) {
        this.commandId = commandId;
        this.timestamp = timestamp;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCommandId() { return commandId; }
    public long getTimestamp() { return timestamp; }
    public String getBuyOrderId() { return buyOrderId; }
    public String getSellOrderId() { return sellOrderId; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
