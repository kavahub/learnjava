package io.github.kavahub.learnjava.spliterator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
    private String name;
    private int relatedArticleId;  


    public Author(String name, int relatedArticleId) {
        this.name = name;
        this.relatedArticleId = relatedArticleId;
    }


    @Override
    public String toString() {
        return "[name: " + name + ", relatedId: " + relatedArticleId + "]";
    }
}
