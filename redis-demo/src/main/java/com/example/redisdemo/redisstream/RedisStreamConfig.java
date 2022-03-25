package com.example.redisdemo.redisstream;

import com.example.redisdemo.redisstream.ListenerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;


@Configuration
public class RedisStreamConfig {

    private final static Logger log = LoggerFactory.getLogger(ListenerMessage.class);
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> ocrfristMyGroupStreamContainer(
            RedisConnectionFactory connectionFactory,
            ListenerMessage streamListener) {
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container =
                streamContainer("mystream",  connectionFactory, streamListener);
        container.start();
        return container;
    }

    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamContainer(String mystream , RedisConnectionFactory connectionFactory, ListenerMessage streamListener){
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .batchSize(10)
                .targetType(String.class)
                .executor(threadPoolTaskExecutor)
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container = StreamMessageListenerContainer
                .create(connectionFactory, options);

        //指定消费最新的消息
        StreamOffset<String> offset = StreamOffset.create(mystream, ReadOffset.lastConsumed());

        //创建消费者
        Consumer consumer = Consumer.from("group-1", "consumer-2");

        StreamMessageListenerContainer.StreamReadRequest<String> streamReadRequest = StreamMessageListenerContainer.StreamReadRequest.builder(offset)
                .errorHandler((error)->log.error(error.getMessage()))
                .cancelOnError(e -> false)
                .consumer(consumer)
                //关闭自动ack确认
                .autoAcknowledge(false)
                .build();
        //指定消费者对象
        container.register(streamReadRequest,streamListener);
        return container;
    }

}
