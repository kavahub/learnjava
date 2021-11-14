package io.github.kavahub.learnjava.common.eventbus;

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
