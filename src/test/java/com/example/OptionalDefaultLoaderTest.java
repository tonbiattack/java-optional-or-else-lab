package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class OptionalDefaultLoaderTest {
    @Test
    void 値が存在すればフォールバックを実行しない() {
        AtomicInteger calls = new AtomicInteger();
        String actual = new OptionalDefaultLoader().load(Optional.of("cached"), calls);
        System.out.println("[evidence] value=" + actual + " fallbackCalls=" + calls.get());
        assertEquals("cached", actual);
        assertEquals(0, calls.get());
    }
}
