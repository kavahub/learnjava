package io.github.kavahub.learnjava.sequence;

public class SequenceGenerator {
    private int currentValue = 0;

    public int getNextSequence() {
        return currentValue++;
    }
}
