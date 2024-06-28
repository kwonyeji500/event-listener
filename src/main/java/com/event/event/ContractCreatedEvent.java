package com.event.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractCreatedEvent {
    private final String email;
    private final String phoneNum;
    private final String recipientName;
    private final String contractUrl;
    private final String contractStatus;
}
