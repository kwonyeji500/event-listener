package com.event.config.feign;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new CustomRuntimeException(F_400_MESSAGE);
            case 500:
            case 505:
            case 503:
                return new CustomRuntimeException(F_5xx_MESSAGE);
            default:
                return new CustomRuntimeException(F_NONE_MESSAGE);
        }
    }

}
