package io.github.kavahub.learnjava.socket;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link DataInTLVFormatClient} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class DataInTLVFormatManualTest {
    @Test
    public void givenServerAndClient_whenClientSendsAndServerReceivesData_thenCorrect() throws InterruptedException {
        //Run server in new thread
        Runnable runnable1 = () -> { runServer(); };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        
        //Wait for 3 seconds 
        TimeUnit.SECONDS.sleep(3);

        //Run client in a new thread
        Runnable runnable2 = () -> { runClient(); };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
    
    public static void runServer() {
        //Run Server
        DataInTLVFormatServer server = new DataInTLVFormatServer();
        server.runServer(5555);
    }
    
    public static void runClient() {
        //Run Client
        DataInTLVFormatClient client = new DataInTLVFormatClient();
        client.runClient("127.0.0.1", 5555);
    }    
}
