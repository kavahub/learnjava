package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorBuffer;

/**
 * 简单功能展示
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class SimpleFeaturesTest {
    private final static String NEW_LINE = System.lineSeparator();

    @Test
    public void renderByAttr() {
        String template = "hi <name>!";
        ST st = new ST(template);
        st.add("name", "Ter");
        assertEquals("hi Ter!", st.render());
    }

    @Test
    public void catchError() {
        ErrorBuffer error = new ErrorBuffer();
        STGroup group = new STGroup();
        group.setListener(error);

        String template = "hi <name>!";
        ST st = new ST(group, template);
        st.add("name1", "Ter");

        assertEquals("hi !", st.render());
        assertThat(error.errors.get(0).toString()).contains("attribute name isn't defined");
    }

    @Test
    public void renderByMultiAttr() {
        ST st = new ST("<items:{it|<it.id>: <it.lastName>, <it.firstName>\n}>");
        st.addAggr("items.{ firstName ,lastName, id }", "Ter", "Parr", 99);
        st.addAggr("items.{firstName, lastName ,id}", "Tom", "Burns", 34);
        String expecting = "99: Parr, Ter" + NEW_LINE +
                "34: Burns, Tom" + NEW_LINE;
        assertEquals(expecting, st.render());
    }

    @Test
    public void renderByProp() {
        String template = "<u.id>: <u.name>"; 
        ST st = new ST(template);
        st.add("u", new User(1, "parrt"));
        assertEquals("1: parrt", st.render());
    }

    @Test
    public void renderByMethod() {
        String template = "<t.manager>"; // call isManager
        ST st = new ST(template);
        st.add("t", new User(32, "Ter"));
        assertEquals("true", st.render());
    }


    public static class User {
        private int id;
        private String name;
        public User(int id, String name) { this.id = id; this.name = name; }
        public boolean isManager() { return true; }
        public boolean hasParkingSpot() { return true; }
        public String getName() { return name; }
        public int getId() { return id; }
    }
}
