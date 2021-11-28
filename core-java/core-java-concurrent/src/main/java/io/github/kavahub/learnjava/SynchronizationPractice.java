package io.github.kavahub.learnjava;


/**
 * 
 * 同步 {@code synchronized} 实践
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class SynchronizationPractice {
    /**
     * 最佳实践
     */
    public static class BestPractice {

        private final String stringLock = new String("LOCK_STRING"); 
        public void stringSolution() { 
            synchronized (stringLock) { 
                // ... 
            }
        }
    
        private int count = 0;
        private final Integer intLock = Integer.valueOf(count);
        public void boxedPrimitiveSolution() {
            synchronized(intLock) {
                count++; 
                // ... 
            } 
        }
    
        private static int staticCount = 0;
        private static final Object staticObjLock = new Object();
        public void staticVariableSolution() {
            synchronized(staticObjLock) {
                staticCount++; 
                // ... 
            } 
        }
    
    }

    /**
     * 不好的实践
     */
    public class BadPractice {

        public void stringBadPractice1() { 
            String stringLock = "LOCK_STRING"; 
            synchronized (stringLock) { 
                // ... 
            }
        }
    
        private final String stringLock = "LOCK_STRING"; 
        public void stringBadPractice2() { 
            synchronized (stringLock) { 
                // ... 
            }
        }
    
        private final String internedStringLock = new String("LOCK_STRING").intern(); 
        public void stringBadPractice3() { 
            synchronized (internedStringLock) { 
                // ... 
            }
        }
    
        private final Boolean booleanLock = Boolean.FALSE; 
        public void booleanBadPractice() { 
            synchronized (booleanLock) { 
                // ...
            }
        }
    
        private int count = 0; 
        private final Integer intLock = count; 
        public void boxedPrimitiveBadPractice() { 
            synchronized (intLock) { 
                count++; 
                // ...
            }
        }
    
    }
    
}
