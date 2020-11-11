package jp.co.tlzs.weget.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.tlzs.weget.redis.model.Auth;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories("jp.co.tlzs.weget.redis.repository.redis")
public class RedisConfig {

    @Bean
    public  ReactiveRedisTemplate<String, String> stringValuesTemplate(
            ReactiveRedisConnectionFactory connectionFactory)
    {
        return new ReactiveRedisTemplate(connectionFactory, RedisSerializationContext.string());
    }

    @Bean
    ReactiveRedisOperations<String, Auth> authTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Auth> serializer = new Jackson2JsonRedisSerializer<>(Auth.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Auth> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Auth> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
    @Bean
    RedisTemplate<String, Auth> authTemplateSync(RedisConnectionFactory connectionFactory , ObjectMapper objectMapper) {
        RedisTemplate<String, Auth> redisTemplate = new RedisTemplate<String, Auth>();
        redisTemplate.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer serializer =new  Jackson2JsonRedisSerializer(Auth.class);
        serializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }
}
