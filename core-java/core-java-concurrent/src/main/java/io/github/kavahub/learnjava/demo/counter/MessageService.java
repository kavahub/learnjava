package io.github.kavahub.learnjava.demo.counter;

public class MessageService {
    private final String message;
    
    public MessageService(String message) {
        this.message = message;
    }
    
    public String getMesssage() {
        return message;
    } 
}
