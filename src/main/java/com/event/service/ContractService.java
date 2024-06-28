package com.event.service;

import com.event.dto.ContractFirstCreateCommand;
import com.event.entity.Contract;
import com.event.entity.User;
import com.event.event.ContractCreatedEvent;
import com.event.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final ContractRepository contractRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void createContract(ContractFirstCreateCommand command) {
        User user = command.getUser();

        Contract contract = contractRepository.save(Contract.create(user));

        ContractCreatedEvent event = new ContractCreatedEvent(
                user.getEmail(),
                user.getPhoneNum(),
                user.getContractRecipient(),
                contract.getUrl(),
                contract.getStatus());
        eventPublisher.publishEvent(event);
    }
}
