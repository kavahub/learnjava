

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.namespace.QName;

import com.sun.xml.ws.client.ClientTransportException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.HelloWS;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;

/**
 * 需要启动服务才能测试
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class HelloWSManualTest {
    private final static Path WDSL_FILE = Paths.get("src", "main", "resources", "hello.wsdl");
    private final static String SERVICE_URL = "http://localhost:9080/jaxws-tomcat-auth/services";
    private final static String NAMESPACE_URI = "http://learnjava.kavahub.github.io/";

    private final static QName SERVICE_QNAME = new QName(NAMESPACE_URI, "HelloWSImplService");
    private final static QName PORT_QNAME = new QName(NAMESPACE_URI, "HelloWSImplPort");
    private final static String WSDL_URL = SERVICE_URL + "/hello?wsdl";
  
    private static Service service;

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        service = Service.create(WDSL_FILE.toUri().toURL(), SERVICE_QNAME);
    }

    @Test
    public void givenAuth_whenHello() {
        HelloWS remoteService = service.getPort(PORT_QNAME, HelloWS.class);
        BindingProvider bp = (BindingProvider) remoteService;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "learnjava");
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "123456");
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,WSDL_URL);

        assertEquals("Hello java", remoteService.hello("java"));
    }

    @Test
    public void whenHello() {
        HelloWS remoteService = service.getPort(PORT_QNAME, HelloWS.class);
        assertThatThrownBy(() -> remoteService.hello("java")).isInstanceOf(ClientTransportException.class);
    }
}
