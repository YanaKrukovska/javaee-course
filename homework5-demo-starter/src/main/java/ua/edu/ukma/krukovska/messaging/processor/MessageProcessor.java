package ua.edu.ukma.krukovska.messaging.processor;

import org.springframework.beans.factory.InitializingBean;

public interface MessageProcessor extends InitializingBean {
    String process(String message);
    String getProcessorName();
}
