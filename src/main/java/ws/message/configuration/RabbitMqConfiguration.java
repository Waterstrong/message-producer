package ws.message.configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMqConfiguration {
    private static final String TOPIC = "topic";

    @Value("${message.queue.host}")
    private String queueHost;

    @Value("${message.exchange.name}")
    private String exchangeName;

    @Bean
    public Channel topicChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, TOPIC, true, false, false, null);
        return channel;
    }
}
