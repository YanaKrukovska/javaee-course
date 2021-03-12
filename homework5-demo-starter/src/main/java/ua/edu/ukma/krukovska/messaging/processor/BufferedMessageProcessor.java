package ua.edu.ukma.krukovska.messaging.processor;

import ua.edu.ukma.krukovska.messaging.buffer.MessageBuffer;

public class BufferedMessageProcessor implements MessageProcessor{
    private static final String NAME  = "Buffered Message Processor" ;
    private MessageBuffer buffer;


    public BufferedMessageProcessor(MessageBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public String process(String message) {
        buffer.putMessage(message);

        return buffer.getMessage() + " processed by " + NAME;
    }

    @Override
    public String getProcessorName() {
        return NAME;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("### Bean initialisation completed " + getProcessorName());
    }
}
