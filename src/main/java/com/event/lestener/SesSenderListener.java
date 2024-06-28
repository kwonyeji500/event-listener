package com.event.lestener;

import com.event.dto.SesDto;
import com.event.event.ContractCreatedEvent;
import com.event.service.SesSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SesSenderListener {
    private final SesSenderService sesSenderService;

    @Async
    @EventListener
    public void handleContractCreatedEvent(ContractCreatedEvent event) {
        log.info("[SesSenderListener] handleContractCreatedEvent - Sending SES email");
        SesDto sesDto = new SesDto(event.getEmail(), "Contract Created", "Your contract has been created.", event.getContractUrl(), event.getContractStatus());
        sesSenderService.send(sesDto);
    }

}
