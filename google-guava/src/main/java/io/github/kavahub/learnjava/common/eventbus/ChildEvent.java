package io.github.kavahub.learnjava.common.eventbus;

public class ChildEvent extends FatherEvent {
    public ChildEvent(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "ChildEvent[name=" + name + "]";
    }
}
