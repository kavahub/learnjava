package io.github.kavahub.learnjava;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Configuration
public class ServiceConfiguration {
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new StudentWSImpl());
        endpoint.publish("http://localhost:9080/apache-cxf-spring/services/student");
        return endpoint;
    }
}
