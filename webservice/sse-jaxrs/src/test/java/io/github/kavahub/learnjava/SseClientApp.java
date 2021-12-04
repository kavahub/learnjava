package io.github.kavahub.learnjava;

import java.util.function.Consumer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

/**
 * 运行 main 前，先运行服务端。
 * 
 * <p>
 * 运行客户端后，持续接收到消息
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class SseClientApp {
    private static final String url = "http://127.0.0.1:9080/sse-jaxrs/sse/stock/prices";

    public static void main(String... args) throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        try (SseEventSource eventSource = SseEventSource.target(target).build()) {

            eventSource.register(onEvent, onError, onComplete);
            eventSource.open();

            //Consuming events for one minute
            Thread.sleep(60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.close();
        System.out.println("End");
    }

    // A new event is received
    private static Consumer<InboundSseEvent> onEvent = (inboundSseEvent) -> {
        String data = inboundSseEvent.readData();
        System.out.println(data);
    };

    //Error
    private static Consumer<Throwable> onError = (throwable) -> {
        throwable.printStackTrace();
    };

    //Connection close and there is nothing to receive
    private static Runnable onComplete = () -> {
        System.out.println("Done!");
    };
}
