package io.github.kavahub.learnjava.publish;

import io.github.kavahub.learnjava.ws.StudentWSImpl;
import io.github.kavahub.learnjava.ws.WelcomeWSImpl;
import jakarta.xml.ws.Endpoint;
import lombok.experimental.UtilityClass;

/**
 * 发布工具
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@UtilityClass
public class PublishUtils {
   private final static String PREFIX = "http://localhost:9080/jaxws/services";

   public void publish() {
      Endpoint.publish(PREFIX + "/student", new StudentWSImpl());
      Endpoint.publish(PREFIX + "/welcome", new WelcomeWSImpl());
   }
}
