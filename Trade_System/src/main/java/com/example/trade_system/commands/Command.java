package com.example.trade_system.commands;

public interface Command {
    String getCommandId();
    long getTimestamp();
}
