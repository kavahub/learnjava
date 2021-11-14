package io.github.kavahub.learnjava.sequence;

public class SequenceGeneratorUsingSynchronizedMethod extends SequenceGenerator {
    @Override
    public synchronized int getNextSequence() {
        return super.getNextSequence();
    }
    
}
