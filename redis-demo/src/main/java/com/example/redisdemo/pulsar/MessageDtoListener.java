package com.example.redisdemo.pulsar;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.api.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MessageDtoListener implements MessageListener {
    @Autowired
    PulsarClient pulsarClient;

    @SneakyThrows
    @Override
    public void received(Consumer consumer, Message msg) {
        Transaction transaction = pulsarClient.newTransaction().withTransactionTimeout(5, TimeUnit.SECONDS).build().get();
        try {
            log.info("Message received: " + new String(msg.getData())+ "key: "+msg.getKey());
            consumer.acknowledgeAsync(msg.getMessageId(),transaction);
            transaction.commit();
        } catch (Exception e) {
            consumer.negativeAcknowledge(msg);
        }
    }
}



