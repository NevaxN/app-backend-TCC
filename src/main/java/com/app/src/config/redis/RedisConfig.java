package com.app.src.config.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

@Bean
    public ObjectMapper hibernateAwareObjectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        
        objectMapper.registerModule(new Hibernate6Module());
        
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        return objectMapper;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(ObjectMapper hibernateAwareObjectMapper) {

        GenericJackson2JsonRedisSerializer jacksonSerializer = 
            new GenericJackson2JsonRedisSerializer(hibernateAwareObjectMapper);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer));
    }
}