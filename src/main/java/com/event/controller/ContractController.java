package com.event.controller;

import com.event.dto.ContractFirstCreateCommand;
import com.event.entity.User;
import com.event.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContractController {
    private final ContractService contractService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CommonResult createContract(@AuthenticationPrincipal User user) {

        contractService.createContract(ContractFirstCreateCommand.create(user));

        return responseService.getSuccessResult();
    }
}
