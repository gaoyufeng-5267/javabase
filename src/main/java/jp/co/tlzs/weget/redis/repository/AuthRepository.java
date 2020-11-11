package jp.co.tlzs.weget.redis.repository;

import jp.co.tlzs.weget.redis.model.Auth;

import java.time.Duration;

public interface AuthRepository {
     void save(Auth value, Duration timeout);
}
