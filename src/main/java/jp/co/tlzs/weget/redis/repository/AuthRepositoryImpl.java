package jp.co.tlzs.weget.redis.repository;

import jp.co.tlzs.weget.redis.model.Auth;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class AuthRepositoryImpl implements AuthRepository{

    private ReactiveRedisOperations<String, Auth> authTemplate;
    private RedisTemplate<String, Auth> authTemplateSync;
    private final String KEY_PREFIX = "auth:";

    public AuthRepositoryImpl(ReactiveRedisOperations<String, Auth> reactiveRedisOperations,
                              RedisTemplate<String, Auth> redisTemplate){
        this.authTemplate= reactiveRedisOperations;
        this.authTemplateSync = redisTemplate;

    }
    @Override
    public void save(Auth value, Duration timeout) {
        authTemplate.opsForValue().set(KEY_PREFIX + value.getId(),value, timeout).subscribe();
    }
}
