package ua.edu.ukma.krukovska.messaging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.ukma.krukovska.messaging.servise.MessageService;

@SpringBootTest
class MessagingApplicationTests {

    @Autowired
    private MessageService messageService;


    @Test
    void contextLoads() {

        String val= messageService.processMessage("|Kotlin is better than Java|");
        System.out.println(val);
    }

}
