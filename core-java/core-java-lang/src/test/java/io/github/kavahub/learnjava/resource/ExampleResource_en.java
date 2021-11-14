package io.github.kavahub.learnjava.resource;

import java.util.ListResourceBundle;

public class ExampleResource_en extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { 
            { "greeting", "hello" } 
        };
    }

    
}
