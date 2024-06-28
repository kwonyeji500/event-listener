package com.event.lestener;

import com.event.dto.SMSPerDto;
import com.event.event.ContractCreatedEvent;
import com.event.service.MessageSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsSenderListener {

    private final MessageSendService messageSendService;

    @Async
    @EventListener
    public void handleContractCreatedEvent(ContractCreatedEvent event) {
        log.info("[SmsSenderListener] handleContractCreatedEvent - Sending SMS");
        SMSPerDto smsDto = SMSPerDto.builder().trPhone(event.getPhoneNum()).build();
        messageSendService.sendSMSPer(smsDto);
    }
}