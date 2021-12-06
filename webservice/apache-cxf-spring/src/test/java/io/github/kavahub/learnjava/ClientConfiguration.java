package io.github.kavahub.learnjava;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 客户端配置
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

        return proxyFactory;
    }
}
