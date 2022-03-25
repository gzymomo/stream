/*
package com.example.redisdemo.pulsar;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PulsarRealConsumer {

    private final static Logger log = LoggerFactory.getLogger(PulsarRealConsumer.class);

    //@PulsarConsumer(topic="bootTopic", clazz= MessageDto.class)
    public void consume(MessageDto message) {
        log.info("PulsarRealConsumer consume id:{},content:{}",message.getId(),message.getContent());
    }
}
*/
