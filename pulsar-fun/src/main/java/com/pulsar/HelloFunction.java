package com.pulsar;

import java.util.function.Function;

/**
 * @author: Zhiyong.ge
 * @Date: 2022/03/25 15:38
 */
public class HelloFunction implements Function<String, String> {

    @Override
    public String apply(String input) {
        return String.format("hello, %s!", input);
    }
}
