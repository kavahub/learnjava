package io.github.kavahub.learnjava.common.eventbus;

/**
 * 
 * 自定义事件
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CustomEvent {
    private String action;
    
    public CustomEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
