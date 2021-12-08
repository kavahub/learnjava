package io.github.kavahub.learnjava;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.antlr.Java8Lexer;
import io.github.kavahub.learnjava.antlr.Java8Parser;

/**
 * {@link UppercaseMethodListener} 示例
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class JavaParserTest {
    @Test
    public void whenOneMethodStartsWithUpperCase_thenOneErrorReturned() throws Exception{

        String javaClassContent = "public class SampleClass { void DoSomething(){} }";
        Java8Lexer java8Lexer = new Java8Lexer(CharStreams.fromString(javaClassContent));
        CommonTokenStream tokens = new CommonTokenStream(java8Lexer);
        Java8Parser java8Parser = new Java8Parser(tokens);
        ParseTree tree = java8Parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        UppercaseMethodListener uppercaseMethodListener = new UppercaseMethodListener();
        walker.walk(uppercaseMethodListener, tree);

        assertThat(uppercaseMethodListener.getErrors().size(), is(1));
        assertThat(uppercaseMethodListener.getErrors().get(0),
                is("Method DoSomething is uppercased!"));
    }
}
