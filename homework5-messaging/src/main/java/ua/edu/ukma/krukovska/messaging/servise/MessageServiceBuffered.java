package ua.edu.ukma.krukovska.messaging.servise;

import ua.edu.ukma.krukovska.messaging.processor.MessageProcessor;

public class MessageServiceBuffered implements MessageService{
    private final MessageProcessor processor;

    public MessageServiceBuffered(MessageProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String processMessage(String message) {
        return processor.process(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("### Bean initialisation completed MessageServiceBuffered");
    }
}
