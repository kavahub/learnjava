package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.Recursion.*;

/**
 * {@link Recursion} 类型示例 
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RecursionTest {
    @Test
    public void testPowerOf10() {
        int p0 = powerOf10(0);
        int p1 = powerOf10(1);
        int p4 = powerOf10(4);
        
        assertEquals(1, p0);
        assertEquals(10, p1);
        assertEquals(10000, p4);
    }
    
    @Test
    public void testFibonacci() {
        int n0 = fibonacci(0);
        int n1 = fibonacci(1);
        int n7 = fibonacci(7);
        
        assertEquals(0, n0);
        assertEquals(1, n1);
        assertEquals(13, n7);
    }
    
    @Test
    public void testToBinary() {
        String b0 = toBinary(0);
        String b1 = toBinary(1);
        String b10 = toBinary(10);
        
        assertEquals("0", b0);
        assertEquals("1", b1);
        assertEquals("1010", b10);
    }
    
    @Test
    public void testCalculateTreeHeight() {
        BinaryNode root = new BinaryNode(1);
        root.setLeft(new BinaryNode(1));
        root.setRight(new BinaryNode(1));
        
        root.getLeft().setLeft(new BinaryNode(1));
        root.getLeft().getLeft().setRight(new BinaryNode(1));
        root.getLeft().getLeft().getRight().setLeft(new BinaryNode(1));
        
        root.getRight().setLeft(new BinaryNode(1));
        root.getRight().getLeft().setRight(new BinaryNode(1));
        
        int height = calculateTreeHeight(root);
        
        assertEquals(4, height);
    }
   
}
