package tayduong.com.demord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import tayduong.com.demord.model.NotifyEvent;

@SpringBootApplication
@EnableScheduling
public class DemoRdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRdApplication.class, args);
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Customize the Redis standalone configuration
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(6379);
        redisConfig.setPassword("password");

        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, NotifyEvent> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, NotifyEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(NotifyEvent.class)); // todo change with custom event

        return template;
    }
}
