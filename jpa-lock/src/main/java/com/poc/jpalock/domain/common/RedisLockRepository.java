package com.poc.jpalock.domain.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisLockRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public Boolean lock(final Long key) {
        final Boolean lock = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
        return lock;
    }

    public Boolean unLock(final Long key) {
        return stringRedisTemplate.delete(generateKey(key));
    }

    private String generateKey(final Long key) {
        return key.toString();
    }
}
