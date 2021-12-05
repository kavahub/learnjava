package io.github.kavahub.learnjava;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 * SSE 请求接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@ApplicationScoped
@Path("stock")
public class SseResource {
 
    @Inject
    private StockService stockService;

    private Sse sse;
    // 所有连接的客户端广播相同的SSE流
    private SseBroadcaster sseBroadcaster;
    // 向连接的客户端发送SSE流
    private OutboundSseEvent.Builder eventBuilder;

    @Context
    public void setSse(Sse sse) {
        this.sse = sse;
        this.eventBuilder = sse.newEventBuilder();
        this.sseBroadcaster = sse.newBroadcaster();
    }

    /**
     * 获取价格
     * @param sseEventSink
     * @param lastReceivedId
     */
    @GET
    @Path("prices")
    @Produces("text/event-stream")
    public void getStockPrices(@Context SseEventSink sseEventSink,
                               @HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastReceivedId) {

        int lastEventId = 1;
        if (lastReceivedId != -1) {
            lastEventId = ++lastReceivedId;
        }
        boolean running = true;
        while (running) {
            // 取除一条股票数据
            Stock stock = stockService.getNextTransaction(lastEventId);
            if (stock != null) {
                // 发布配置
                OutboundSseEvent sseEvent = this.eventBuilder
                        .name("stock")
                        .id(String.valueOf(lastEventId))
                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                        .data(Stock.class, stock)
                        .reconnectDelay(3000)
                        .comment("price change")
                        .build();
                // 发布
                sseEventSink.send(sseEvent);
                lastEventId++;
            }
            //Simulate connection close
            if (lastEventId % 5 == 0) {
                sseEventSink.close();
                break;
            }

            try {
                //Wait 5 seconds
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ex) {
                // ...
            }
            //Simulatae a while boucle break
            running = lastEventId <= 2000;
        }
        sseEventSink.close();
    }

    @GET
    @Path("subscribe")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void listen(@Context SseEventSink sseEventSink) {
        sseEventSink.send(sse.newEvent("Welcome !"));
        this.sseBroadcaster.register(sseEventSink);
        sseEventSink.send(sse.newEvent("You are registred !"));
    }

    /**
     * 发布数据
     */
    @GET
    @Path("publish")
    public void broadcast() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int lastEventId = 0;
                boolean running = true;
                while (running) {
                    lastEventId++;
                    // 取出需要发布的数据
                    Stock stock = stockService.getNextTransaction(lastEventId);
                    if (stock != null) {
                        // 发布配置
                        OutboundSseEvent sseEvent = eventBuilder
                                .name("stock")
                                .id(String.valueOf(lastEventId))
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .data(Stock.class, stock)
                                .reconnectDelay(3000)
                                .comment("price change")
                                .build();
                        // 发布
                        sseBroadcaster.broadcast(sseEvent);
                    }
                    try {
                        //Wait 5 seconds
                        TimeUnit.SECONDS.sleep(5*1000);
                    } catch (InterruptedException ex) {
                        // ...
                    }
                    //Simulatae a while boucle break
                    running = lastEventId <= 2000;
                }
            }
        };
        new Thread(r).start();
    }   
}
