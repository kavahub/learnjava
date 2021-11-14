package io.github.kavahub.learnjava.spliterator;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    private List<Author> listOfAuthors;
    private int id;
    private String name;
    
    public Article(String name) {
        this.name = name;
    }

    public Article(List<Author> listOfAuthors, int id) {
        this.listOfAuthors = listOfAuthors;
        this.id = id;
    }
    
    
}
