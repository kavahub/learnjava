package io.github.kavahub.learnjava.synthetic;

/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SyntheticMethod {
/**
     * Class which contains two synthetic methods accessors to a private field.
     * 
     *
     */
    class NestedClass {

        /**
         * Field for which will be generated synthetic methods accessors. It's
         * important that this field is private for this purpose.
         */
        private String nestedField;
    }

    /**
     * Gets the private nested field. We need to read the nested field in order
     * to generate the synthetic getter.
     * 
     * @return the {@link NestedClass#nestedField}
     */
    public String getNestedField() {
        return new NestedClass().nestedField;
    }

    /**
     * Sets the private nested field. We need to write the nested field in order
     * to generate the synthetic setter.
     * 
     * @param nestedField
     *            the {@link NestedClass#nestedField}
     */
    public void setNestedField(String nestedField) {
        new NestedClass().nestedField = nestedField;
    }    
}
