package io.github.kavahub.learnjava.common.eventbus;

import lombok.Getter;

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
