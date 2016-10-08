package ws.message.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMqConfiguration {

    @Value("${message.queue.host}")
    private String queueHost;

    @Value("${message.queue.username}")
    private String username;

    @Value("${message.queue.password}")
    private String password;

    @Value("${message.queue.port:5672}")
    private int port;

    @Value("${message.queue.virtualHost:/}")
    private String virtualHost;

    @Value("${message.exchange.name}")
    private String exchangeName;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public AbstractConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        return new CachingConnectionFactory(factory);
    }
}
