package io.github.kavahub.learnjava.common.eventbus;

public class FatherEvent extends GrandpaEvent {
    public FatherEvent(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "FatherEvent[name=" + name + "]";
    }
}
