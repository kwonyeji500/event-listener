package com.event.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SMSPerDto extends MessageDto {

    private String trPhone;

}
