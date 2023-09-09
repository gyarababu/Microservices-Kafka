package com.springboot.order.kafka;

import com.springboot.basedomains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    // sending the message using a method
    public void sendMessage(OrderEvent orderEvent){
        // event logs
        LOGGER.info(String.format("Order event => %s", orderEvent.toString()));

        // creating message object
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                // setting key and value
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        // sending the message to topic
        kafkaTemplate.send(message);
    }
}
