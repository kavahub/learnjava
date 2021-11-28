package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link MessageService}回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MessageServiceCallable implements Callable<String> {
    
    private final MessageService messageService;
    
    public MessageServiceCallable(MessageService messageService) {
        this.messageService = messageService;
    
    }
    
    @Override
    public String call() {
        return messageService.getMesssage();
    }
    
}
