package io.simplemap;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentTimeawareMapTest {

    static TimeawareMap<String, String> concurrentTimeawareMap;

    static Clock fixedClock;

    @BeforeAll
    public static void init() {
        concurrentTimeawareMap = new ConcurrentTimeawareMap<>();
        fixedClock = Clock.fixed(Instant.ofEpochSecond(3600), ZoneId.systemDefault());
        ReflectionTestUtils.setField(concurrentTimeawareMap, "clock", fixedClock);
    }

    @Test
    public void putWhenTest(){
        concurrentTimeawareMap.putWhen("Test", "Test Value");
        Map<Long, String> timeValueMap = new ConcurrentHashMap<>();
        timeValueMap.put(fixedClock.millis(), "Test Value");
        assertEquals(timeValueMap, concurrentTimeawareMap.get("Test"));
    }

    @Test
    public void getWhenTest(){
        concurrentTimeawareMap.putWhen("Test", "Test Value");
        Map<Long, String> timeValueMap = new ConcurrentHashMap<>();
        timeValueMap.put(fixedClock.millis(), "Test Value");
        assertEquals("Test Value", concurrentTimeawareMap.getWhen("Test", 3600000l));
    }

}