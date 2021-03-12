package ua.edu.ukma.krukovska.messaging.processor;

public class DefaultMessageProcessor implements MessageProcessor{
    private static final String NAME  = "Default Message Processor" ;
    @Override
    public String process(String message) {
        return message + " processed by " + NAME;
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
