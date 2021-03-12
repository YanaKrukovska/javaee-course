package ua.edu.ukma.krukovska.messaging.servise;

import org.springframework.beans.factory.InitializingBean;

public interface MessageService extends InitializingBean {
    String processMessage(String message);
}
