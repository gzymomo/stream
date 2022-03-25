package com.example.redisdemo.pulsar;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.api.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Zhiyong.Ge
 */
@Service
public class PulsarConsumer {

    @Autowired MessageDtoListener messageDtoListener;
    @Autowired PulsarClient pulsarClient;


    @Value("${pulsar.topic}")
    String topic ;
    @Value("${pulsar.subscription}")
    String subscription ;

    @PostConstruct
    public void  consumer(){
        try {
            pulsarClient.newConsumer(Schema.JSON(MessageDto.class))
                    .consumerName("local-consumer")
                    .topic(topic)
                    .subscriptionName(subscription)
                    //指定消费模式，包含：Exclusive，Failover，Shared，Key_Shared。默认Exclusive模式
                    //- exclusive：消费组里有且仅有一个consumer能够进行消费，其它的根本连不上pulsar；
                    //- failover：消费组里的每个消费者都能连上每个partition所在的broker，但有且仅有一个consumer能消费到数据。当这个消费者崩溃了，其它的消费者会被选出一个来接班；
                    //- shared：消费组里所有消费者都能消费topic中的所有partition，消息以round-robin的方式来分发；
                    //- key-shared：消费组里所有消费者都能消费到topic中所有partition，但是带有相同key的消息会保证发送给同一个消费者。
                    .subscriptionType(SubscriptionType.Shared)
                    //指定从哪里开始消费还有Latest，valueof 可选，默认Latest
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                    .messageListener(messageDtoListener)
                    //指定消费失败后延迟多久broker重新发送消息给consumer，默认60s
                    .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                    .subscribe();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
