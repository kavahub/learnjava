package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.kavahub.learnjava.antlr.Java8BaseListener;
import io.github.kavahub.learnjava.antlr.Java8Parser;

/**
 * 方法首字母大写监听，提示首字母大写的方法
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class UppercaseMethodListener extends Java8BaseListener {

    private List<String> errors = new ArrayList<String>();

    @Override
    public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        TerminalNode node = ctx.Identifier();
        String methodName = node.getText();

        if (Character.isUpperCase(methodName.charAt(0))){
            errors.add(String.format("Method %s is uppercased!", methodName));
        }
    }

    public List<String> getErrors(){
        return Collections.unmodifiableList(errors);
    }
    
}
