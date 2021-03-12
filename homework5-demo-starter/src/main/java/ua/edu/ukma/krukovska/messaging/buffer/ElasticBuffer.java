package ua.edu.ukma.krukovska.messaging.buffer;

import java.util.LinkedList;
import java.util.List;

public class ElasticBuffer implements MessageBuffer {
    private List<String> storage = new LinkedList<>();


    @Override
    public void putMessage(String message) {
        storage.add(message);

    }

    @Override
    public String getMessage() {
        return storage.remove(0);
    }

    @Override
    public String getConfiguration() {
        return "ElasticBuffer" ;
    }
    @Override
    public void afterPropertiesSet()   {
        System.out.println("### Bean initialisation completed " + getConfiguration());
    }
}
