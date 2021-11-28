package io.github.kavahub.learnjava.counter;

/**
 * 
 * 消息服务
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MessageService {
    private final String message;
    
    public MessageService(String message) {
        this.message = message;
    }
    
    public String getMesssage() {
        return message;
    } 
}
