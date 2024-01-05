package com.zj.auction.seckill.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Configuration
public class LocalDateTimeSerializerConfig {

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss") ;

        public LocalDateTimeSerializer localDateTimeDeserializer() {
            return new LocalDateTimeSerializer(formatter);
        }

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
            // 默认LocalDateTime格式化的格式 yyyy-MM-dd HH:mm:ss
            return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
        }

}
