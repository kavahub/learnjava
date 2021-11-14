package io.github.kavahub.learnjava.sequence;

public class SequenceGeneratorUsingSynchronizedBlock extends SequenceGenerator {
    private Object mutex = new Object();

    @Override
    public int getNextSequence() {
        synchronized (mutex) {
            return super.getNextSequence();
        }
    }

}
