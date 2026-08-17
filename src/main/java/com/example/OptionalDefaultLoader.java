package com.example;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class OptionalDefaultLoader {
    public String load(Optional<String> value, AtomicInteger fallbackCalls) {
        return value.orElse(loadFallback(fallbackCalls));
    }

    private String loadFallback(AtomicInteger fallbackCalls) {
        fallbackCalls.incrementAndGet();
        return "fallback";
    }
}
