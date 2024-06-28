package com.event.dto;

import com.event.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContractFirstCreateCommand {

    private final User user;

    public static ContractFirstCreateCommand create(User loginUser) {
        return ContractFirstCreateCommand.builder()
                .user(loginUser)
                .build();
    }
}
