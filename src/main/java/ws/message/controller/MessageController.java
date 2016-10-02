package ws.message.controller;

import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rabbitmq.client.Channel;

@RestController
public class MessageController {
    private final static Logger LOGGER = getLogger(MessageController.class);

    @Value("${message.exchange.name}")
    private String exchangeName;

    @Autowired
    private Channel topicChannel;

    @RequestMapping(method = POST, value = "/messages")
    public ResponseEntity<?> sendMessage(@RequestBody String message) throws IOException {
        LOGGER.info(String.format("@%s Sent message: %s", now(), message));
        topicChannel.basicPublish(exchangeName, "", null, message.getBytes());
        return new ResponseEntity<>(message, CREATED);
    }
}