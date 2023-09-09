package com.springboot.order.controller;

import com.springboot.basedomains.dto.Order;
import com.springboot.basedomains.dto.OrderEvent;
import com.springboot.order.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    // creating post REST API
    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        // generating random orderId
        order.setOrderId(UUID.randomUUID().toString());

        // setting the order details
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order status is in pending state");
        orderEvent.setOrder(order);

        // creating the message
        orderProducer.sendMessage(orderEvent);

        // returning the message
        return "Order placed successfully!";
    }
}
