package ua.edu.ukma.krukovska.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.edu.ukma.krukovska.messaging.servise.MessageService;

@SpringBootApplication
public class MessagingApplication {

    @Autowired
    private MessageService messageService;


    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }

}
