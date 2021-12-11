package io.github.kavahub.learnjava;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 需要处理的目标类
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class TargetClass {

    public void method1() throws InterruptedException{
        log.info("method1 called ");
        Thread.sleep((new Random()).nextInt(1000));
    }

    public String method2(){
        log.info("method2 called ");
        
        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "end";
    }
}
