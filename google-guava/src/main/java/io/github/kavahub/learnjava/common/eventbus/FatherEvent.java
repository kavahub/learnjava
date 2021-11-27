package io.github.kavahub.learnjava.common.eventbus;

/**
 * 
 * 父事件
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FatherEvent extends GrandpaEvent {
    public FatherEvent(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "FatherEvent[name=" + name + "]";
    }
}
