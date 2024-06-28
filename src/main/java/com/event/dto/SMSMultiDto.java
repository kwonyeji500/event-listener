package com.event.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class SMSMultiDto extends MessageDto {
    private List<String> trPhone;
}
