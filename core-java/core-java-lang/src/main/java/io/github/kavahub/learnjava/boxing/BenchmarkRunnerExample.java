package io.github.kavahub.learnjava.boxing;

/**
 * 对象类型与基本类型的性能测试
 */
public class BenchmarkRunnerExample {

    public static void main(String[] args) throws Exception {
        new IntPrimitiveLookup().run();
        new IntegerWrapperLookup().run();
        new FloatPrimitiveLookup().run();
        new FloatWrapperLookup().run();
        new DoublePrimitiveLookup().run();
        new DoubleWrapperLookup().run();
        new ShortPrimitiveLookup().run();
        new ShortWrapperLookup().run();
        new BooleanPrimitiveLookup().run();
        new BooleanWrapperLookup().run();
        new CharPrimitiveLookup().run();
        new CharacterWrapperLookup().run();
        new BytePrimitiveLookup().run();
        new ByteWrapperLookup().run();
        new LongPrimitiveLookup().run();
        new LongWrapperLookup().run();
    }
}
