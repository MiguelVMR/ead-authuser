package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class UserEventPublisher
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/01/2025
 */
@Component
public class UserEventPublisher {

    final RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserEvent(UserEventDto userEventDto) {
        rabbitTemplate.convertAndSend(exchangeUserEvent,"", userEventDto);
    }
}
