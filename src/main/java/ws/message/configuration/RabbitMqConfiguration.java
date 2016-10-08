package ws.message.configuration;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMqConfiguration {

    @Value("${message.topic.host}")
    private String topicHost;

    @Value("${message.topic.username}")
    private String username;

    @Value("${message.topic.password}")
    private String password;

    @Value("${message.topic.port:5672}")
    private int port;

    @Value("${message.topic.virtualHost:/}")
    private String virtualHost;

    @Value("${message.topic.connectionTimeout}")
    private int connectionTimeout;

    @Value("${message.topic.recoveryInterval}")
    private long recoveryInterval;

    @Bean
    public AbstractConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(topicHost);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        factory.setConnectionTimeout(connectionTimeout);
        factory.setNetworkRecoveryInterval(recoveryInterval);
        return new CachingConnectionFactory(factory);
    }
}
