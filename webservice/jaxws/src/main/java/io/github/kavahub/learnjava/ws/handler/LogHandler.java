package io.github.kavahub.learnjava.ws.handler;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class LogHandler implements SOAPHandler<SOAPMessageContext>  {@Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        if (log.isDebugEnabled()) {
            log(context);
        }
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (log.isDebugEnabled()) {
            log(context);
        }
        return true;
    }
    
    private void log(SOAPMessageContext context) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            context.getMessage().writeTo(baos);
            log.debug(baos.toString());
            baos.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
