package io.github.kavahub.learnjava;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 学生接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@XmlJavaTypeAdapter(StudentAdapter.class)
public interface Student {
    public String getName();
}
