package com.example.redisdemo.pulsar;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StringMessageListener implements MessageListener {
    @Autowired
    PulsarClient pulsarClient;

    @SneakyThrows
    @Override
    public void received(Consumer consumer, Message msg) {
        try {
            log.info("Message received: " + new String(msg.getData())+ "key: "+msg.getKey());
            consumer.acknowledgeAsync(msg);
        } catch (Exception e) {
            consumer.negativeAcknowledge(msg);
        }
    }
}



