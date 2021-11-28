package io.github.kavahub.learnjava.synthetic;

/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SyntheticConstructor {
 /**
     * We need to instantiate the {@link NestedClass} using a private
     * constructor from the enclosing instance in order to generate a synthetic
     * constructor.
     */
    @SuppressWarnings("unused")
    private NestedClass nestedClass = new NestedClass();

    /**
     * Class which contains a synthetic constructor.
     * 
     *
     */
    class NestedClass {

        /**
         * In order to generate a synthetic constructor, this class must have a
         * private constructor.
         */
        private NestedClass() {
        }
    }   
}
