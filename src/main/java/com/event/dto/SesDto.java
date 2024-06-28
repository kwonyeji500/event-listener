package com.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class SesDto {
    private String subject;
    private String templateName;
    private Map<String, Object> variables;
    private String to;
}
