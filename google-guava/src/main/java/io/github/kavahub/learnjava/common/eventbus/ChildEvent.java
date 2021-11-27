package io.github.kavahub.learnjava.common.eventbus;

/**
 * 
 * 子事件
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ChildEvent extends FatherEvent {
    public ChildEvent(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "ChildEvent[name=" + name + "]";
    }
}
