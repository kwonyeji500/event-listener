package com.event.config.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (!requestTemplate.url().contains("/api/v1/mms/upload")) { // 예제 조건, 실제 조건에 맞게 수정하세요.
                requestTemplate.header("Content-Type", "application/json");
                requestTemplate.header("Accept", "application/json");
                return;
            }
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Content-Type", "multipart/form-data");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new MessageFeignErrorDecoder();
    }

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {

        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

}
