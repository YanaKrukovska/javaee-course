package ua.edu.ukma.krukovska.messaging.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.ukma.krukovska.messaging.buffer.BoundedBuffer;
import ua.edu.ukma.krukovska.messaging.buffer.ElasticBuffer;
import ua.edu.ukma.krukovska.messaging.buffer.MessageBuffer;
import ua.edu.ukma.krukovska.messaging.processor.BufferedMessageProcessor;
import ua.edu.ukma.krukovska.messaging.processor.DefaultMessageProcessor;
import ua.edu.ukma.krukovska.messaging.processor.MessageProcessor;

@Configuration
@EnableConfigurationProperties(MessageProcessorProperties.class)
public class MessageProcessorAutoConfiguration {

    @Autowired
    private MessageProcessorProperties messageProcessorProperties ;

    @Bean(name = "messageProcessor")
    @ConditionalOnProperty(prefix = "message.processor", name = "enableBuffering", havingValue =  "true")
    public MessageProcessor messageProcessor() {
        return new BufferedMessageProcessor(messageBuffer());
    }

    @Bean(name = "defaultMessageProcessor")
    @ConditionalOnProperty(prefix = "message.processor", name = "enableBuffering", havingValue =  "false", matchIfMissing = true)
    public MessageProcessor defaultMessageProcessor() {
        return new DefaultMessageProcessor();
    }


    @Bean
    @ConditionalOnProperty(prefix = "message.processor", name = "enableBuffering", havingValue =  "true")
    public MessageBuffer messageBuffer() {
        if (messageProcessorProperties.getBufferSize() > 0) {
            return new BoundedBuffer(messageProcessorProperties.getBufferSize());
        } else {
            return new ElasticBuffer();
        }
    }
}
