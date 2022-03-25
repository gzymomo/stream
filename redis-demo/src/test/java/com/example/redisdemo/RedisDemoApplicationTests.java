package com.example.redisdemo;

import com.example.redisdemo.redisstream.ListenerMessage;
import io.lettuce.core.RedisBusyException;
import io.lettuce.core.RedisCommandExecutionException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class RedisDemoApplicationTests {

	private final static Logger log = LoggerFactory.getLogger(ListenerMessage.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate ;


	@Test
	void contextLoads() {

		int i = 0;

		while (i < 10){
			ObjectRecord<String, String> record = ObjectRecord.create("mystream","asdfasdf"+i);
			RecordId mystream = stringRedisTemplate.opsForStream().add(record);
			System.out.println(mystream.getValue());
			i++;
		}

	}

	private void createConsumerGroup(String key, String group, RedisTemplate redisTemplate) {
		try {
			//redisTemplate.opsForStream().createGroup("my-stream", ReadOffset.from("0-0"), "my-group");
			redisTemplate.opsForStream().createGroup("event-stream", "my-group-2");
		} catch (RedisSystemException e) {
			if (e.getRootCause().getClass().equals(RedisBusyException.class)) {
				log.info("STREAM - Redis group already exists, skipping Redis group creation: my-group-2");
			} else if (e.getRootCause().getClass().equals(RedisCommandExecutionException.class)) {
				log.info("STREAM - Stream does not yet exist, creating empty stream: event-stream");
				redisTemplate.opsForStream().add("event-stream", Collections.singletonMap("", ""));
				redisTemplate.opsForStream().createGroup("event-stream", "my-group-2");
			} else throw e;
		}
	}

	@Test
	void redisget() {
		Consumer consumer = Consumer.from("group-1", "consumer-1");
		ObjectRecord<String,String> record = ObjectRecord.create("mystream","asdfasdf4");
		record.withId(RecordId.of("1647850128786-0"));
		List<MapRecord<String, Object, Object>> read = stringRedisTemplate.opsForStream().read(consumer, StreamOffset.create("mystream",ReadOffset.from("1647850128786-0")));
		System.out.println(read.size());
	}


	@Test
	void redisloglog() {
		List<String> item = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			item.add("test-"+i);
		}
		stringRedisTemplate.opsForHyperLogLog().add("hll",item.toArray(new String[item.size()]));
		Long hll = stringRedisTemplate.opsForHyperLogLog().size("hll");

		System.out.println(hll);
	}


	@Test
	void redisGeo() {

		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.374284,31.488446),"1");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.347295,31.495059),"2");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.347232,31.492329),"3");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.342325,31.44329),"4");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.323495,31.434559),"5");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.347295,31.4345),"6");
		stringRedisTemplate.opsForGeo().add("geotest",new Point(120.334595,31.33459),"7");

		Distance geotest = stringRedisTemplate.opsForGeo().distance("geotest", "1", "2", Metrics.KILOMETERS);
		System.out.println(geotest.getValue());

		GeoResults<RedisGeoCommands.GeoLocation<String>> geotest1 = stringRedisTemplate.opsForGeo().radius("geotest", "1", new Distance(100, Metrics.KILOMETERS));
		System.out.println(geotest1);
	}
}
