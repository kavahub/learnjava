package io.github.kavahub.learnjava;

import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        log.info("Main is running - {}", Main.class.getName());

        TargetClass target = new TargetClass();
        target.method1();
        target.method2();
    }
}
