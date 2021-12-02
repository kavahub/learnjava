package io.github.kavahub.learnjava;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import io.github.kavahub.learnjava.ws.StudentWS;
import io.github.kavahub.learnjava.ws.WelcomeWS;
import io.github.kavahub.learnjava.ws.handler.AuthenticationHandler.ClientAuthenticationHandler;
import io.github.kavahub.learnjava.ws.handler.LogHandler;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import lombok.experimental.UtilityClass;

/**
 * 远程接口工具
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@UtilityClass
public class ServiceHelper {
    private final static String SERVICE_URL = "http://localhost:9080/jax-ws/services";
    private final static String NAMESPACE_URI = "http://ws.learnjava.kavahub.github.io/";

    private final static QName STUDENT_SERVICE_QN_NAME = new QName(NAMESPACE_URI, "StudentWSImplService");
    private final static QName STUDENT_PORT_QNAME = new QName(NAMESPACE_URI, "StudentWSImplPort");
    private final static String STUDENT_WSDL_URL = SERVICE_URL + "/student?wsdl";
  
    private final static QName WELCOME_SERVICE_QNAME = new QName(NAMESPACE_URI, "WelcomeWSImplService");
    private final static QName WELCOME_PORT_QNAME = new QName(NAMESPACE_URI, "WelcomeWSImplPort");
    private final static String WELCOME_WSDL_URL = SERVICE_URL + "/welcome?wsdl";

    public WelcomeWS getWelcomeWS() {
        return WelcomeServiceCreator.INSTANCE.get();
    }
    
    public StudentWS getStudentWS() {
        return StudentServiceCreator.INSTANCE.get();
    }

    public void addStudentHandler() {
        StudentServiceCreator.INSTANCE.addHandler();
    }


    /**
     * 
     * Welcome远程接口创建，懒创建
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    private static class WelcomeServiceCreator {
        public static final WelcomeServiceCreator INSTANCE = new WelcomeServiceCreator();

        private Service service;

        private synchronized void init(){
            if (service != null) {
                return;
            }
            try {
                service = Service.create(new URL(WELCOME_WSDL_URL), WELCOME_SERVICE_QNAME);
            } catch (MalformedURLException e) {
                throw new RuntimeException("创建远程服务异常", e);
            }
        }
        
        /**
         * 获取远程服务
         * 
         * @return
         */
        public WelcomeWS get() {
            if (service == null) {
                init();
            }
            return service.getPort(WELCOME_PORT_QNAME, WelcomeWS.class);
        }

    }

    /**
     * 
     * Student远程接口创建，懒创建
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    private static class StudentServiceCreator {
        public static final StudentServiceCreator INSTANCE = new StudentServiceCreator();

        private Service service;

        private synchronized void init(){
            if (service != null) {
                return;
            }
            try {
                service = Service.create(new URL(STUDENT_WSDL_URL), STUDENT_SERVICE_QN_NAME);
            } catch (MalformedURLException e) {
                throw new RuntimeException("创建远程服务异常", e);
            }
        }
        
        /**
         * 添加 Handler：安全认证和日志记录
         */
        public void addHandler() {
            if (service == null) {
                init();
            }

            service.setHandlerResolver(new HandlerResolver() {
                @SuppressWarnings("rawtypes")
                @Override
                public List<Handler> getHandlerChain(PortInfo portInfo) {
                    List<Handler> handlerList = new ArrayList<Handler>();
                    // 安全认证
                    handlerList.add(new ClientAuthenticationHandler());
                    // 日志记录
                    handlerList.add(new LogHandler());
                    return handlerList;
                }
            });
        }

        /**
         * 获取远程服务
         * @return
         */
        public StudentWS get() {
            if (service == null) {
                init();
            }
            return service.getPort(STUDENT_PORT_QNAME, StudentWS.class);
        }

    }

}
