package com.event.client;

import com.event.dto.SMSMultiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "MessageFeignClient" , contextId = "feignClientForMall", url = "${external.message.url}", configuration = MessageFeignConfig.class)
public interface MessageFeignClient {

    @PostMapping(value = "/api/v1/sms/per", headers = "authorization=" + "${external.message.token}")
    MessageResponse sendSMSPer(SMSPerDto request);

    @PostMapping(value = "/api/v1/sms", headers = "authorization=" + "${external.message.token}")
    MessageResponse sendSMSMulti(SMSMultiDto request);

}
