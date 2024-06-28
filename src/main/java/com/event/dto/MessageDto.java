package com.event.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class MessageDto {

    /*
    메세지 전송시간
    null인 경우 현재시간으로 보냄
    현재보다 미래시간 입력시 예약발송됨
     */
    // 수신자
    private LocalDateTime trSendDate;

    /*
    하위 필드값은 필수입니다.
     */
    // 메시지 내용(중복내용 연속으로 보내면 메시지 전송안됨)
    private String trMsg;

    // 발신자
    private String trCallback;

    // 고정값
    private final String trMsgType = "0";

}
