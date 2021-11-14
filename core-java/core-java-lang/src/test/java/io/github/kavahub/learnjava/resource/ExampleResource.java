package io.github.kavahub.learnjava.resource;

import java.util.ListResourceBundle;

public class ExampleResource extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { 
            { "greeting", "" } 
        };
    }

    
}
