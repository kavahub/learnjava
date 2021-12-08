package io.github.kavahub.learnjava;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.DateRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import lombok.Getter;

/**
 * EMail模板渲染
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class EmailRenderManualTest {
    static STGroup group = new STGroupFile("email.stg");

    @Test
    public void render() {
        ST st = group.getInstanceOf("email");
        st.add("order", order);
        st.add("separator", "----------------");
        
        System.out.println(st.render());
    }

    @Test
    public void renderWithFormater() {
        STGroup test = new STGroupFile("email.stg");
        test.registerRenderer(Date.class, new DateRenderer());
        test.registerRenderer(BigDecimal.class, new BigDecimalRenderer());
        ST st = test.getInstanceOf("email");
        st.add("order", order);
        st.add("separator", "----------------");
        
        System.out.println(st.render());
    }

    public static class BigDecimalRenderer implements AttributeRenderer<BigDecimal> {
        private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(
                "##,##0.00", DecimalFormatSymbols.getInstance(Locale.GERMANY));
        private static final String EURO_CHARACTER = "\u20AC";

        @Override
        public String toString(BigDecimal value, String formatString, Locale locale) {
            if (formatString.equals("currency")) {
                    NumberFormat numberFormat = DECIMAL_FORMAT;
                    String formatted = numberFormat.format(value) + " "
                            + EURO_CHARACTER;
                    return formatted;
            }
            return value.toString();
        }
    }
    
    public static Order order;
    static {
        Calendar instance = GregorianCalendar.getInstance(Locale.GERMAN);
        instance.set(2011, Calendar.JANUARY, 28);
        Date orderDate = instance.getTime();

        Customer customer = new Customer("Oliver", "Zeigermann",
                "Gaußstraße 180\n" + "22765 Hamburg\n" + "GERMANY");
        order = new Order(customer, orderDate);

        Article article1 = new Article("How to become famous", new BigDecimal(
                "17.80"));
        order.getItems().add(new Item(1, article1));

        Article article2 = new Article("Cool stuff", new BigDecimal("1.00"));
        order.getItems().add(new Item(2, article2));
    }
    
    @Getter
    public static class Article {
        public final String name;
        public final BigDecimal price;
    
        public Article(String name, BigDecimal price) {
            super();
            this.name = name;
            this.price = price;
        }
    
    }

    @Getter
    public static class Item {
        private final int amount;
        private final Article article;
    
        public Item(int amount, Article article) {
            super();
            this.amount = amount;
            this.article = article;
        }
    
        public BigDecimal getSubTotal() {
            return article.price.multiply(new BigDecimal(amount));
        }
    
    }

    
    @Getter
    public static class Order {
        public final static BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("20.00");
        public final static BigDecimal SHIPPING_COSTS = new BigDecimal("3.00");
        
        private final Customer customer;
        private final Date orderDate;
        private final List<Item> items = new ArrayList<Item>();
    
        public Order(Customer customer, Date orderDate) {
            super();
            this.customer = customer;
            this.orderDate = orderDate;
        }
    
        public BigDecimal getTotal() {
            return getTotalWithoutShipping().add(getShippingCost());
        }
        
        public BigDecimal getShippingCost() {
            if (isFreeShipping()) {
                return new BigDecimal("0");
            } else {
                return SHIPPING_COSTS;
            }
        }
    
        public BigDecimal getTotalWithoutShipping() {
            BigDecimal total = new BigDecimal(0);
            for (Item item : items) {
                BigDecimal part = item.getSubTotal();
                total = total.add(part);
            }
            return total;
        }
    
        // introduced for st3 and st4 as they can not compare values
        public boolean isFreeShipping() {
            return getTotalWithoutShipping().compareTo(FREE_SHIPPING_THRESHOLD) == 1;
        }
    }

    @Getter
    public static class Customer {
        private final String firstName;
        private final String lastName;
        private final String address;
    
        public Customer(String firstName, String lastName, String address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
        }
    
    }
}
