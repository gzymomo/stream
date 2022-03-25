package com.pulsar;

import org.apache.pulsar.functions.api.SerDe;

import java.util.regex.Pattern;

/**
 * @author: Zhiyong.ge
 * @Date: 2022/03/25 16:00
 */
public class MessageDtoSerde implements SerDe<MessageDto> {
    public MessageDto deserialize(byte[] input) {
        String s = new String(input);
        String[] fields = s.split(Pattern.quote("|"));
        return new MessageDto(fields[0], fields[1]);
    }

    public byte[] serialize(MessageDto input) {
        return "%s|%s".format(input.getId(), input.getContent()).getBytes();
    }
}