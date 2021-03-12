package ua.edu.ukma.krukovska.messaging.buffer;

import org.springframework.beans.factory.InitializingBean;

public interface MessageBuffer extends InitializingBean {
    void putMessage(String message);
    String getMessage();
    String getConfiguration();
}
