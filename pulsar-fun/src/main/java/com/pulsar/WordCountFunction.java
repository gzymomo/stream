package com.pulsar;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

import java.util.Arrays;

/**
 * @author: Zhiyong.ge
 * @Date: 2022/03/25 15:41
 */
public class WordCountFunction  implements Function<String, String> {

    @Override
    public String process(String input, Context context) throws Exception {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(input.split(" ")).forEach(word -> {
            String counterKey = word.toLowerCase();
            context.incrCounter(counterKey, 1);
            sb.append( counterKey+": "+ context.getCounter(counterKey));
        });


        return sb.toString();
    }
}