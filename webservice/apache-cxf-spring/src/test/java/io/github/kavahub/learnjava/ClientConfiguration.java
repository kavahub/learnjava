package io.github.kavahub.learnjava;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Configuration
public class ClientConfiguration {
    @Bean(name = "client")
    public Object generateProxy() {
        return proxyFactoryBean().create();
    }

    @Bean
    public JaxWsProxyFactoryBean proxyFactoryBean() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(StudentWS.class);
        proxyFactory.setAddress("http://localhost:9080/apache-cxf-spring/services/student");
        //proxyFactory.setWsdlURL("http://localhost:9080/services/student?wsdl");
        return proxyFactory;
    }
}
