package io.github.kavahub.learnjava.spliterator;

import java.util.List;

import lombok.Data;

/**
 * 
 * 文章
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Data
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
