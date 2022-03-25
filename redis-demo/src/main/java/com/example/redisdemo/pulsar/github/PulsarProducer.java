/*
package com.example.redisdemo.pulsar;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PulsarProducer {
    @Autowired
    private PulsarTemplate template;


    public void send(MessageDto message){
        try {
            template.send("bootTopic",message);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
*/
