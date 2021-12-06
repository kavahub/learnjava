package io.github.kavahub.learnjava;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class ServicesServer {
    public static void main(String[] args) throws InterruptedException {
        StudentWS studentWS = new StudentWSImpl();
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        
        factory.setAddress("http://localhost:9080/apache-cxf-spring/services/student");
        factory.setServiceClass(StudentWS.class);
        factory.setServiceBean(studentWS);

        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getInInterceptors().add(new LoggingOutInterceptor());
        factory.create();

        log.info("Server ready...");
        Thread.sleep(60 * 1000);
        log.info("Server exiting");
        System.exit(0);
    }
}
