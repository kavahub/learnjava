package io.github.kavahub.learnjava.spliterator;

import java.util.Spliterator;
import java.util.concurrent.Callable;

/**
 * 给每篇文章加后缀
 */
public class Task implements Callable<String> {
    private Spliterator<Article> spliterator;
    private final static String SUFFIX = "- published by learnjava";

    public Task(Spliterator<Article> spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public String call() {
        // 统计文章数，并返回
        int current = 0;
        while (spliterator.tryAdvance(article -> {
            article.setName(article.getName()
                .concat(SUFFIX));
        })) {
            current++;
        }
        ;
        return Thread.currentThread()
            .getName() + ":" + current;
    }    
}
