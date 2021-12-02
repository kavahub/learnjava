package io.github.kavahub.learnjava.ws.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import io.github.kavahub.learnjava.SecurityContextHolder;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证处理
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class AuthenticationHandler {
    private final static String NAMESPACEURI = "http://ws.learnjava.kavahub.github.io/";
    private final static QName AUTH_QNAME = new QName(NAMESPACEURI, "authentication");
    private final static QName USERNAME_QNAME = new QName(NAMESPACEURI, "username");
    private final static QName PASSWORD_QNAME = new QName(NAMESPACEURI, "password");

    private final static String USERNAME_TOKEN = "admin";
    private final static String PASSWORD_TOKEN = "098765";

    /**
     * 
     * 服务器端认证。接收客户端请求，解析请求头，验证用户名和密码
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    public static class ServerAuthenticationHandler implements SOAPHandler<SOAPMessageContext> {
        @Override
        public boolean handleMessage(SOAPMessageContext context) {
            Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if (isOutbound) {
                // 响应
                return true;
            }

            boolean success = doLogin(context);
            if (!success) {
                // 登录失败
                throw new RuntimeException("授权校验失败：用户名或密码错误");
            }
            return success;
        }

        /**
         * 登录，校验用户名，密码
         * 
         * @param context
         * @return
         */
        private boolean doLogin(SOAPMessageContext context) {
            SOAPMessage message = context.getMessage();
            try {
                SOAPEnvelope envelop = message.getSOAPPart().getEnvelope();
                SOAPHeader header = envelop.getHeader();

                if (header == null) {
                    return false;
                }

                Iterator<Node> authIt = header.getChildElements(AUTH_QNAME);
                if (!authIt.hasNext()) {
                    return false;
                }

                SOAPElement auth = (SOAPElement) authIt.next();

                // user
                Iterator<Node> userIt = auth.getChildElements(USERNAME_QNAME);
                if (!userIt.hasNext()) {
                    return false;
                }
                SOAPElement user = (SOAPElement) userIt.next();

                // password
                Iterator<Node> passwordIt = auth.getChildElements(PASSWORD_QNAME);
                if (!passwordIt.hasNext()) {
                    return false;
                }
                SOAPElement password = (SOAPElement) passwordIt.next();

                if (user != null && password != null && USERNAME_TOKEN.equals(user.getValue())
                        && PASSWORD_TOKEN.equals(password.getValue())) {
                    // 设置上下文，后续业务使用
                    SecurityContextHolder.INSTANCE.setUsername(user.getValue());
                    return true;
                }

                return false;
            } catch (SOAPException e) {
                log.error("授权校验异常", e);
            }

            return false;
        }

        @Override
        public boolean handleFault(SOAPMessageContext context) {
            SecurityContextHolder.INSTANCE.remove();
            return true;
        }

        @Override
        public void close(MessageContext context) {
            SecurityContextHolder.INSTANCE.remove();
        }

        @Override
        public Set<QName> getHeaders() {
            return null;
        }

    }

    /**
     * 
     * 客户端认证，将用户名，密码加入到请求头，发送到服务器端
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    public static class ClientAuthenticationHandler implements SOAPHandler<SOAPMessageContext> {

        @Override
        public boolean handleMessage(SOAPMessageContext context) {
            Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if (Boolean.FALSE.equals(isOutbound)) {
                return true;
            }
    
            SOAPMessage message = context.getMessage();
            try {
                SOAPHeader header = message.getSOAPHeader();
                if (header == null) {
                    header = message.getSOAPPart().getEnvelope().addHeader();
                }
    
                SOAPElement auth = header.addChildElement(AUTH_QNAME);
    
                SOAPElement name = auth.addChildElement(USERNAME_QNAME);
                name.addTextNode(USERNAME_TOKEN);
    
                SOAPElement password = auth.addChildElement(PASSWORD_QNAME);
                password.addTextNode(PASSWORD_TOKEN);
    
                message.saveChanges();
            } catch (SOAPException e) {
                log.error("添加授权信息异常", e);
                return false;
            }
    
            return true;
        }
    
        @Override
        public boolean handleFault(SOAPMessageContext context) {
            return true;
        }
    
        @Override
        public void close(MessageContext context) {
    
        }
    
        @Override
        public Set<QName> getHeaders() {
            return null;
        }
    
    }
    
}
