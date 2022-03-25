package com.example.redisdemo.redisstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;


@Component
public class ListenerMessage implements StreamListener<String, ObjectRecord<String,String>> {

    private final static Logger log = LoggerFactory.getLogger(ListenerMessage.class);
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        // 消息ID
        RecordId messageId = message.getId();
        String stream = message.getStream();
        String value = message.getValue();


        log.info("StreamMessageListener1  stream message。messageId: {} ;stream :{} ; values : {}",messageId ,stream,value);
        // 通过RedisTemplate手动确认消息
        redisTemplate.opsForStream().acknowledge("group-1", message);
    }
}