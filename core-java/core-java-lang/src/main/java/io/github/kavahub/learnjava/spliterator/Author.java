package io.github.kavahub.learnjava.spliterator;

import lombok.Data;

@Data
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
