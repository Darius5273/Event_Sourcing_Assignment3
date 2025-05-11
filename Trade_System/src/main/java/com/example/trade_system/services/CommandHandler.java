package com.example.trade_system.services;

import com.example.trade_system.commands.*;
import com.example.trade_system.events.*;

import java.io.IOException;
import java.util.UUID;

public class CommandHandler {

    private final EventStore eventStore;

    public CommandHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void handle(Command command) throws IOException {
        if (command instanceof PlaceOrderCommand) {
            handlePlaceOrder((PlaceOrderCommand) command);
        } else if (command instanceof CancelOrderCommand) {
            handleCancelOrder((CancelOrderCommand) command);
        } else if (command instanceof ExecuteTradeCommand) {
            handleExecuteTrade((ExecuteTradeCommand) command);
        } else if (command instanceof DebitFundsCommand) {
            handleDebitFunds((DebitFundsCommand) command);
        } else if (command instanceof CreditFundsCommand) {
            handleCreditFunds((CreditFundsCommand) command);
        } else {
            throw new IllegalArgumentException("Unhandled command type: " + command.getClass().getSimpleName());
        }
    }

    private void handlePlaceOrder(PlaceOrderCommand cmd) throws IOException {
        OrderPlaced event = new OrderPlaced(
                generateId(), System.currentTimeMillis(),
                cmd.getOrderId(), cmd.getUserId(),
                cmd.getSide(), cmd.getPrice(), cmd.getQuantity()
        );
        eventStore.append(event);
    }

    private void handleCancelOrder(CancelOrderCommand cmd) throws IOException {
        OrderCancelled event = new OrderCancelled(
                generateId(), System.currentTimeMillis(),
                cmd.getOrderId()
        );
        eventStore.append(event);
    }

    private void handleExecuteTrade(ExecuteTradeCommand cmd) throws IOException {
        TradeExecuted event = new TradeExecuted(
                generateId(), System.currentTimeMillis(),
                cmd.getBuyOrderId(), cmd.getSellOrderId(),
                cmd.getPrice(), cmd.getQuantity()
        );
        eventStore.append(event);
    }

    private void handleDebitFunds(DebitFundsCommand cmd) throws IOException {
        FundsDebited event = new FundsDebited(
                generateId(), System.currentTimeMillis(),
                cmd.getUserId(), cmd.getAmount()
        );
        eventStore.append(event);
    }

    private void handleCreditFunds(CreditFundsCommand cmd) throws IOException {
        FundsCredited event = new FundsCredited(
                generateId(), System.currentTimeMillis(),
                cmd.getUserId(), cmd.getAmount()
        );
        eventStore.append(event);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
