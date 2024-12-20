package tayduong.com.demord.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import tayduong.com.demord.model.NotifyEvent;

@Configuration(proxyBeanMethods = false)
public class RedisConfig {

    @Bean
    ReactiveRedisOperations<String, NotifyEvent> eventRedisOperations(ReactiveRedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<NotifyEvent> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(NotifyEvent.class);

        return new ReactiveRedisTemplate<>(redisConnectionFactory,
                RedisSerializationContext.<String, NotifyEvent>newSerializationContext()
                        .key(RedisSerializer.string())
                        .value(jsonRedisSerializer)
                        .hashKey(RedisSerializer.string())
                        .hashValue(jsonRedisSerializer)
                        .build());
    }
}
