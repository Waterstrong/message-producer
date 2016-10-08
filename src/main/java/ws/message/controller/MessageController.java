package ws.message.controller;

import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final static Logger LOGGER = getLogger(MessageController.class);

    @Value("${message.topic.name}")
    private String topicName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(method = POST, value = "/messages")
    public ResponseEntity<?> sendMessage(@RequestBody String message) throws IOException {
        LOGGER.info(format("@%s Send message: %s", now(), message));
        rabbitTemplate.convertAndSend(topicName, "", message);
        return new ResponseEntity<>(message, CREATED);
    }

    @RequestMapping(method = GET, value = "/messages")
    public ResponseEntity<?> getMessage() {
        LOGGER.info(format("@%s GET Request", now()));
        return new ResponseEntity<>(format("@%s Please use POST http://localhost:8081/producer/messages", now()), OK);
    }
}
