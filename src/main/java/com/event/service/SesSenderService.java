package com.event.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.event.dto.SesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class SesSenderService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final TemplateEngine templateEngine;
    @Value("${aws.ses.from}")
    private String from;

    public void send(SesDto dto) {
        try {
            String content = templateEngine.process(dto.getTemplateName(), createContext(dto.getVariables()));
            SendEmailRequest sendEmailRequest = createSendEmailRequest(dto.getSubject(), content, dto.getTo());
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
            log.info("Email sent successfully to {}", dto.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to {}. Error: {}", dto.getTo(), e.getMessage(), e);
        }
    }

    private Context createContext(Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return context;
    }

    private SendEmailRequest createSendEmailRequest(String subject, String content, String... to) {
        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withSource(from)
                .withMessage(new Message()
                        .withSubject(new Content().withCharset(StandardCharsets.UTF_8.name()).withData(subject))
                        .withBody(new Body().withHtml(new Content().withCharset(StandardCharsets.UTF_8.name()).withData(content)))
                );
    }

}
