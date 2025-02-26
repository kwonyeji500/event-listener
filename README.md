# 프로젝트 설명

이 프로젝트는 이메일 및 문자 전송 서비스를 이벤트 리스너로 분리하여 비동기 처리함으로써 응답 시간을 평균 3초에서 100ms 이하로 대폭 줄이고 시스템 효율성을 향상시킨 예제입니다. 이는 계약서 생성과 관련된 HTTP API 호출 후 SES 이메일 및 SMS 메시지를 비동기로 전송하는 방식으로 구현되었습니다.

## 문제 설명

기존 시스템에서는 계약서 생성과 함께 이메일 및 SMS를 동기적으로 전송하고 있었습니다. 이로 인해 다음과 같은 문제가 발생했습니다:

1. **응답 시간 지연**: 이메일 및 SMS 전송 시간이 포함되어 계약서 생성 API의 응답 시간이 평균 3초에 달했습니다.
2. **시스템 효율성 저하**: 동기적인 전송 방식으로 인해 API 서버의 처리 효율성이 떨어지고, 서버 자원이 비효율적으로 사용되었습니다.
3. **사용자 경험 저하**: 느린 응답 시간으로 인해 사용자 경험이 저하되었으며, 특히 다수의 사용자가 동시에 요청할 경우 병목 현상이 발생했습니다.

## 해결책 추리

위 문제를 해결하기 위해 다음과 같은 방안을 고려했습니다:

1. **비동기 처리 도입**: 이메일 및 SMS 전송을 비동기로 처리하여 API 응답 시간 단축.
2. **이벤트 리스너 활용**: Spring의 이벤트 리스너를 사용하여 이메일 및 SMS 전송 로직을 분리.
3. **효율적인 리소스 관리**: 비동기 처리와 이벤트 기반 설계를 통해 서버 자원의 효율적인 사용을 도모.

## 해결 방법

### 1. 비동기 처리 도입

이메일 및 SMS 전송 로직을 비동기로 처리하여 API 응답 시간을 단축했습니다. 이를 위해 Spring의 `@Async` 어노테이션을 사용하여 이벤트 리스너가 비동기적으로 실행되도록 했습니다.

### 2. 이벤트 리스너 활용

이벤트 기반 설계를 도입하여 계약서 생성 후 이메일 및 SMS 전송을 처리했습니다. 계약서가 생성되면 `ContractCreatedEvent` 이벤트를 발행하고, 해당 이벤트를 수신하는 리스너가 이메일 및 SMS를 전송합니다.

### 3. 효율적인 리소스 관리

비동기 처리와 이벤트 기반 설계를 통해 서버의 처리 효율성을 높였습니다. API 서버는 계약서 생성 요청을 빠르게 처리하고, 이메일 및 SMS 전송은 별도의 비동기 작업으로 처리하여 서버 자원을 효율적으로 관리할 수 있게 되었습니다.

## 코드 예시
### 1. 계약서 생성 API
   ```java
@RestController
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CommonResult createContract(@AuthenticationPrincipal User user) {

        contractService.createContract(ContractFirstCreateCommand.create(user));

        return responseService.getSuccessResult();
    }
}
```
### 2. 계약서 서비스
   ```java
@Service
@RequiredArgsConstructor
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
   ```
### 3. 계약서 생성 이벤트
   ```java
@Getter
@AllArgsConstructor
public class ContractCreatedEvent {
    private final String email;
    private final String phoneNum;
    private final String recipientName;
    private final String contractUrl;
    private final String contractStatus;
}
   ```
### 4. SES 이메일 리스너
   ```java
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
   ```
### 5. SMS 전송 리스너
   ```java
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
   ```


## 결론

이 프로젝트는 이메일 및 문자 전송을 비동기적으로 처리하여 API 응답 시간을 크게 줄이고, 시스템 효율성을 향상시켰습니다. 이를 통해 사용자 경험을 개선하고, 서버 자원을 효율적으로 관리할 수 있게 되었습니다.
