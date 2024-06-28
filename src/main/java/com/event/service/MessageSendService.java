package com.event.service;

import com.event.client.MessageFeignClient;
import com.event.dto.SMSMultiDto;
import com.event.dto.SMSPerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSendService {

    private final MessageFeignClient messageClient;

    /*
     Description : SMS 단일발송
     */
    public void sendSMSPer(SMSPerDto request) {
        try {
            log.info("[MessageSendService] sendSMSPer - Request : {}", request);
            MessageResponse response = messageClient.sendSMSPer(request);
            log.info("[MessageSendService] sendSMSPer - Response : {}", response);
        } catch (Exception e) {
            log.error("[MessageSendService] sendSMSPer - Failed to send SMS. Request: {}, Error: {}", request, e.getMessage(), e);
        }
    }

    /*
     Description : SMS 멀티발송
     */
    public void sendSMSMulti(SMSMultiDto request) {
        try {
            log.info("[MessageSendService] sendSMSMulti - Request : {}", request);
            MessageResponse response = messageClient.sendSMSMulti(request);
            log.info("[MessageSendService] sendSMSMulti - Response : {}", response);
        } catch (Exception e) {
            log.error("[MessageSendService] sendSMSMulti - Failed to send SMS. Request: {}, Error: {}", request, e.getMessage(), e);
        }
    }

}
