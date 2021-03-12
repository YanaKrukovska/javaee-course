package ua.edu.ukma.krukovska.messaging.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message.processor")
public class MessageProcessorProperties {
    private boolean enableBuffering = false;
    private int bufferSize = 0;


    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }


    public boolean isEnableBuffering() {
        return enableBuffering;
    }

    public void setEnableBuffering(boolean enableBuffering) {
        this.enableBuffering = enableBuffering;
    }
}
