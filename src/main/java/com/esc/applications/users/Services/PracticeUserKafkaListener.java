package com.esc.applications.users.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PracticeUserKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(PracticeUserKafkaListener.class);

    @KafkaListener(topics = "practice_users-topic", groupId = "practice_users-group")
    public void listen(String message) {
        log.info("Message Received : {}", message);
    }

}
