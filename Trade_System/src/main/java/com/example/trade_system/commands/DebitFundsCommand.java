package com.example.trade_system.commands;

public class DebitFundsCommand implements Command {
    private final String commandId;
    private final long timestamp;
    private final String userId;
    private final String amount;

    public DebitFundsCommand(String commandId, long timestamp, String userId, String amount) {
        this.commandId = commandId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.amount = amount;
    }

    public String getCommandId() { return commandId; }
    public long getTimestamp() { return timestamp; }
    public String getUserId() { return userId; }
    public String getAmount() { return amount; }
}
