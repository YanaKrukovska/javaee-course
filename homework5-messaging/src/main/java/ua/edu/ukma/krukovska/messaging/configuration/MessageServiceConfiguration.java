package ua.edu.ukma.krukovska.messaging.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.ukma.krukovska.messaging.processor.MessageProcessor;
import ua.edu.ukma.krukovska.messaging.servise.MessageService;
import ua.edu.ukma.krukovska.messaging.servise.MessageServiceBuffered;
import ua.edu.ukma.krukovska.messaging.servise.MessageServiceDefault;

@Configuration
@AutoConfigureAfter(MessageProcessorAutoConfiguration.class)
public class MessageServiceConfiguration {

    private final MessageProcessor messageProcessor;

    public MessageServiceConfiguration(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Bean
    @ConditionalOnBean(name = "messageProcessor")
    public MessageService messageServiceBuffered() {
        return new MessageServiceBuffered(messageProcessor);
    }

    @Bean
    @ConditionalOnBean(name = "defaultMessageProcessor")
    public MessageService messageServiceDefault() {
        return new MessageServiceDefault(messageProcessor);
    }

}
