package io.github.kavahub.learnjava.common.eventbus;

import lombok.Getter;

/**
 * 
 * 祖事件
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Getter
public class GrandpaEvent {
    protected String name;

    public GrandpaEvent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GrandpaEvent[name=" + name + "]";
    }

    
}
