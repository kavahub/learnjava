package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

/**
 * Transient 类型序列化
 * 
 * <p>
 * Java中transient关键字的作用，简单地说，就是让某些被修饰的成员属性变量不被序列化，这一看好像很好理解，
 * 就是不被序列化，那么什么情况下，一个对象的某些字段不需要被序列化呢？如果有如下情况，可以考虑使用关键字transient修饰：
 * 类中的字段值可以根据其它字段推导出来，如一个长方形类有三个属性：长度、宽度、面积，那么在序列化的时候，面积这个属性就没必要被序列化了；
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class TransientSerializableTest {

    @Test
    void givenTransient_whenSerDe_thenVerifyValues() throws Exception {
        Book book = new Book();
        book.setBookName("Java Reference");
        book.setDescription("will not be saved");
        book.setCopies(25);

        BookSerializeUtils.serialize(book);
        Book book2 = BookSerializeUtils.deserialize();

        assertEquals("Java Reference", book2.getBookName());
        // 注意，transient类型没有序列化
        assertNull(book2.getDescription());
        assertEquals(0, book2.getCopies());
    }

    @Test
    void givenFinalTransient_whenSerDe_thenValuePersisted() throws Exception {
        Book book = new Book();

        BookSerializeUtils.serialize(book);
        Book book2 = BookSerializeUtils.deserialize();

        assertEquals("Fiction", book2.getBookCategory());
    }

    @AfterAll
    public static void cleanup() {
        File file = new File(BookSerializeUtils.fileName);
        file.deleteOnExit();
    }

    @Getter
    @Setter
    public static class Book implements Serializable {

        private static final long serialVersionUID = -2936687026040726549L;

        private String bookName;
        private transient String description;
        private transient int copies;
        private final transient String bookCategory = "Fiction";

    }

    public static class BookSerializeUtils {
        static String fileName = "book.ser";

        /**
         * Method to serialize Book objects to the file
         * 
         * @throws FileNotFoundException
         */
        public static void serialize(Book book) throws Exception {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(book);

            out.close();
            file.close();
        }

        /**
         * Method to deserialize the person object
         * 
         * @return book
         * @throws IOException, ClassNotFoundException
         */
        public static Book deserialize() throws Exception {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            Book book = (Book) in.readObject();

            in.close();
            file.close();

            return book;
        }

    }
}
