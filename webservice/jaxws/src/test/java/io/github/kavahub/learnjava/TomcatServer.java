package io.github.kavahub.learnjava;

import java.util.function.Consumer;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

/**
 * 内嵌 {@link Tomcat} 操作，支持用户自定义 {@link Connector} 和 {@link Context} 
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TomcatServer {
    private static final int DEFAULT_PORT = 9080;
    private static final String DEFAULT_CONTEXT_PATH = "/jax-ws";
    private static final String DOC_BASE = ".";
    private static final String APP_BASE = "./target/classes";


    public final static TomcatServer INSTANCE = new TomcatServer();
    private Tomcat tomcat;
    private Connector connector;
    private Context context;
    private Consumer<Context> contextOpts = (context) -> {};
    private Consumer<Connector> connectorOpts = (connector) -> {}; 

    private TomcatServer() {
        init();
    }

    private synchronized void init() {
        if (tomcat != null) {
            return;
        }

        tomcat = new Tomcat();
        tomcat.setBaseDir(DOC_BASE);
        tomcat.getHost().setAppBase(APP_BASE);

        Connector connector = new Connector();
        connector.setPort(DEFAULT_PORT);
        connector.setURIEncoding("UTF-8");
        tomcat.getService().addConnector(connector);

        Context context = tomcat.addWebapp(DEFAULT_CONTEXT_PATH, DOC_BASE);

  
        context.setReloadable(false);
        context.addLifecycleListener(new AprLifecycleListener());

        StandardJarScanner scanner = new StandardJarScanner();
        scanner.setScanClassPath(false);
        scanner.setScanManifest(false);
        context.setJarScanner(scanner);
    }

    /**
     * 用户自定义 {@link Context} 
     * 
     * @param contextOpts
     */
    public void withContextOpts(Consumer<Context> contextOpts) {
        if (contextOpts != null) {
            this.contextOpts = contextOpts;
            this.contextOpts.accept(this.context);
        }
    }

    /**
     * 用户自定义 {@link Connector} 
     * 
     * @param contextOpts
     */
    public void withConnectorOpts(Consumer<Connector> connectorOpts) {
        if (connectorOpts != null) {
            this.connectorOpts = connectorOpts;
            this.connectorOpts.accept(this.connector);
        }
    }

    /**
     * 启动服务
     * 
     * @throws LifecycleException
     */
    public void start() throws LifecycleException {
        tomcat.start();
    }

    /**
     * 阻塞主线程，避免主线程结束导致服务关闭
     * 
     * @throws LifecycleException
     */
    public void await() throws LifecycleException {
        tomcat.getServer().await();
    }

    /**
     * 停止服务
     * 
     * @throws LifecycleException
     */
    public void stop() throws LifecycleException {
        tomcat.stop();
    }

}
