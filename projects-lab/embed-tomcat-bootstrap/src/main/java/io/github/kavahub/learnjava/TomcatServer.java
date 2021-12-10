package io.github.kavahub.learnjava;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import lombok.extern.slf4j.Slf4j;

/**
 * Tomcat服务器
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class TomcatServer implements Server {
    private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 9080;
	private static final String DEFAULT_CONTEXT_PATH = "/";
	private static final String DOC_BASE = ".";
	private static final String ADDITION_WEB_INF_CLASSES = ".";
	private static final String WEB_APP_MOUNT = "/WEB-INF/classes";
	private static final String INTERNAL_PATH = "/";

	@Override
	public void run(String[] args) {
		int port = port(args);
		String host = host(args);
		Tomcat tomcat = tomcat(host, port);

		try {
			tomcat.start();
		} catch (LifecycleException exception) {
			log.error("{}", exception.getMessage());
			log.error("Exit...");
			System.exit(1);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
					log.info("Tomcat server shutdown...");
                    tomcat.getServer().stop();
               } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        }));

		log.info("Application started with URL {}.", DEFAULT_HOST + ":" + port + DEFAULT_CONTEXT_PATH);
		log.info("Hit Ctrl + D or C to stop it...");
		tomcat.getServer().await();
	}

	private int port(String[] args) {
		if (args.length > 2) {
			String port = args[1];
			try {
				return Integer.valueOf(port);
			} catch (NumberFormatException exception) {
				log.error("Invalid port number argument {}", port, exception);
			}
		}

		return DEFAULT_PORT;
	}

	private String host(String[] args) {
		if (args.length > 0) {
			return args[1];
		}

		return DEFAULT_HOST;
	}

	private Tomcat tomcat(String host, int port) {
		Tomcat tomcat = new Tomcat();
		tomcat.setHostname(host);
		tomcat.getHost().setAppBase(DOC_BASE);
		tomcat.setPort(port);
		tomcat.getConnector();
		context(tomcat);

		return tomcat;
	}

	private Context context(Tomcat tomcat) {
		Context context = tomcat.addWebapp(DEFAULT_CONTEXT_PATH, DOC_BASE);

		File classes = new File(ADDITION_WEB_INF_CLASSES);
		String base = classes.getAbsolutePath();
		WebResourceRoot resources = new StandardRoot(context);
		resources.addPreResources(new DirResourceSet(resources, WEB_APP_MOUNT, base, INTERNAL_PATH));
		context.setResources(resources);

		return context;
	}
}
