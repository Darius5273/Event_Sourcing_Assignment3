package com.example.trade_system.api;

import com.example.trade_system.commands.CancelOrderCommand;
import com.example.trade_system.commands.CreditFundsCommand;
import com.example.trade_system.commands.PlaceOrderCommand;
import com.example.trade_system.models.Order;
import com.example.trade_system.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TradingController {

    private final EventStore eventStore;
    private final ReplayEngine replayEngine;
    private final CommandHandler commandHandler;
    private final MatchingEngine matchingEngine;
    private final UserService userService;

    @Autowired
    public TradingController(UserService userService) throws IOException {
        this.eventStore = new EventStore("events.txt");
        this.replayEngine = new ReplayEngine(userService, eventStore);
        this.commandHandler = new CommandHandler(eventStore);
        this.matchingEngine = new MatchingEngine(eventStore);
        this.userService = userService;
        this.replayEngine.replay();
    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody PlaceOrderRequest request) throws IOException {
        String orderId = UUID.randomUUID().toString();
        PlaceOrderCommand cmd = new PlaceOrderCommand(
                "cmd-" + System.currentTimeMillis(),
                System.currentTimeMillis(),
                orderId,
                request.userId(),
                request.side(),
                request.price(),
                request.quantity()
        );
        commandHandler.handle(cmd);
        replayEngine.replay();
        matchingEngine.match(replayEngine.getOrderBook());
        replayEngine.replay();
        return orderId;
    }

    @PostMapping("/funds")
    public void creditFunds(@RequestBody CreditFundsRequest request) throws IOException {
        var amount = new BigDecimal(request.amount());
        if (amount.compareTo(new BigDecimal("0"))<=0) return;
        CreditFundsCommand cmd = new CreditFundsCommand(
                "cmd-" + System.currentTimeMillis(),
                System.currentTimeMillis(),
                request.userId(),
                request.amount()
        );
        commandHandler.handle(cmd);
        replayEngine.replay();
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestBody CancelOrderRequest request) throws IOException {
        var order = replayEngine.getOrderBook().getActiveOrders().get(request.orderId());
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

        if (!order.getUserId().equals(request.userId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only cancel your own orders.");
        }

        CancelOrderCommand cmd = new CancelOrderCommand(
                "cmd-" + System.currentTimeMillis(),
                System.currentTimeMillis(),
                request.orderId()
        );

        commandHandler.handle(cmd);
        replayEngine.replay();
        return ResponseEntity.ok("Order cancelled successfully.");
    }

    @GetMapping("/balances")
    public String getUserBalance(@RequestParam String userId) {
        return replayEngine.getAccount().getBalance(userId).toString();
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrderBook() {
        return replayEngine.getOrderBook().getActiveOrders().values().stream()
                .map(o -> new OrderResponse(
                        o.getOrderId(),
                        userService.getUserById(o.getUserId()),
                        o.getSide(),
                        o.getPrice().toString(),
                        o.getQuantity(),
                        o.getTimestamp()
                ))
                .toList();
    }

    @GetMapping("/orders/{userId}")
    public List<OrderResponse> getUserOrders(@PathVariable String userId) {
        return replayEngine.getOrderBook().getActiveOrders().values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .map(o -> new OrderResponse(
                        o.getOrderId(),
                        userService.getUserById(o.getUserId()),
                        o.getSide(),
                        o.getPrice().toString(),
                        o.getQuantity(),
                        o.getTimestamp()
                ))
                .toList();
    }

    @GetMapping("/history/{userId}")
    public List<String> getUserHistory(@PathVariable String userId) {
        String user = userService.getUserById(userId);
        return replayEngine.getUserHistory(user);
    }

}
