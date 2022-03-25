package com.example.redisdemo;

import com.example.redisdemo.pulsar.MessageDto;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class PulsarDemoTest {
    private final static Logger log = LoggerFactory.getLogger(PulsarDemoTest.class);

    @Autowired
    PulsarClient pulsarClient;


    @Test
    void contextLoads() throws PulsarClientException, ExecutionException, InterruptedException {

        Producer<MessageDto> producer = pulsarClient.newProducer(Schema.JSON(MessageDto.class))
                .topic("exclamation-output")
                .create();
       // Transaction transaction = pulsarClient.newTransaction().withTransactionTimeout(5, TimeUnit.SECONDS).build().get();

        for (int i = 0; i <100; i++) {
            Integer key = i % 3;
            producer.newMessage().key(key.toString()).value(new MessageDto("123"+i,"gzymomo")).send();
            //producer.newMessage(transaction).key("txn").value(new MessageDto("123"+i,"txn gzymomo")).send();
        }




    }

    @Test
    void funTest() throws PulsarClientException, ExecutionException, InterruptedException {

        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .topic("wordcount-input")
                .create();
        // Transaction transaction = pulsarClient.newTransaction().withTransactionTimeout(5, TimeUnit.SECONDS).build().get();

        for (int i = 0; i <3; i++) {
            Integer key = i % 3;
            producer.newMessage().key(key.toString()).value("momo sdf").send();
            //producer.newMessage(transaction).key("txn").value(new MessageDto("123"+i,"txn gzymomo")).send();
        }




    }
}
