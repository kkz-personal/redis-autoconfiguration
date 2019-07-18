package com.example.redisautoconfiguration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * @author kmz
 */
@Configuration
@EnableConfigurationProperties({RedisAutoConfiguration.MainRedisProperties.class,RedisAutoConfiguration.LesserRedisProperties.class})
public class RedisAutoConfiguration {

    /**
     * @Primary 注解告诉Spring遇到多个bean的时候，它为默认
     * prefix  告诉spring读取前缀为spring.main-redis的配置信息
     */
    @Primary
    @ConfigurationProperties(prefix = "spring.main-redis")
    public static class MainRedisProperties extends RedisProperties {
    }

    @ConfigurationProperties(prefix = "spring.lesser-redis")
    public static class LesserRedisProperties extends RedisProperties {
    }

    /**
     * 通过配置获取RedisConnectionFactory
     * @param mainRedisProperties  mainRedis的配置信息
     * @return
     */
    @Primary
    @Bean("mainRedisConnectionFactory")
    public static RedisConnectionFactory getMainRedisConnectionFactory(MainRedisProperties mainRedisProperties){
        RedisConnectionConfiguration factory = new RedisConnectionConfiguration(mainRedisProperties);

        return factory.redisConnectionFactory();
    }

    /**
     * 获取mainredis的StringRedisTemplate实例
     * @param redisConnectionFactory  使用@Qulifier指定需要的factory
     * @return  template
     */
    @Primary
    @Bean("mainStringRedisTemplate")
    public static StringRedisTemplate getMainStringRedisTemplate(
            @Qualifier(value = "mainRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("lesserRedisConnectionFactory")
    public static RedisConnectionFactory getLesserRedisConnectionFactory(LesserRedisProperties lesserRedisProperties){
        RedisConnectionConfiguration factory = new RedisConnectionConfiguration(lesserRedisProperties);

        return factory.redisConnectionFactory();
    }

    @Bean("lesserStringRedisTemplate")
    public static StringRedisTemplate getLesserStringRedisTemplate(
            @Qualifier(value = "lesserRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


}
