package ua.edu.ukma.krukovska.messaging.buffer;

import java.util.ArrayList;
import java.util.List;

public class BoundedBuffer implements MessageBuffer {
    private List<String> storage = new ArrayList<>();
    private int maxSize;

    public BoundedBuffer(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void putMessage(String message) {
        if (storage.size() < maxSize) {
            storage.add(message);
        } else {
            throw new IllegalStateException("Max buffer size has been reached: " + maxSize);
        }
    }

    @Override
    public String getMessage() {
        return storage.remove(0);
    }

    @Override
    public String getConfiguration() {
        return "BoundedBuffer max size =  " + maxSize;
    }

    @Override
    public void afterPropertiesSet()   {
        System.out.println("### Bean initialisation completed " + getConfiguration());
    }
}
