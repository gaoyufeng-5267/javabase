package jp.co.tlzs.weget.redis.repository;

import jp.co.tlzs.weget.redis.model.Auth;
import reactor.core.publisher.Mono;

import java.time.Duration;

public interface AuthRepository {
     void save(Auth value, Duration timeout);
     Mono<Auth> findById(String id );
     Mono<Boolean> expire(String key, Duration timeout );
}
