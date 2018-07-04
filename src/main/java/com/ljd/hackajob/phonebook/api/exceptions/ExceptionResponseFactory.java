package com.ljd.hackajob.phonebook.api.exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ljd.hackajob.phonebook.model.ExceptionResponse;

/**
 * 
 * @author leodavison
 *
 */
public class ExceptionResponseFactory {
    private ExceptionResponseFactory() {
        // not meant to be instantiated
    }
    
    private static final Map<String, Map<Object, Object>> MESSAGES = new ConcurrentHashMap<>();
    
    private static String getMessageFormat(String bundleName, String messageKey) throws IOException {
        Map<Object, Object> bundle = MESSAGES.get(bundleName);
        
        if (bundle == null) {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(bundleName + ".properties");
            Properties p = new Properties();
            p.load(in);
            bundle = p;
            
            Map<Object,Object> existing = MESSAGES.putIfAbsent(bundleName, p);
            
            if (existing != null) {
                bundle = existing;
            }
        }
        
        return (String)bundle.get(messageKey);
    }
    public static Response buildResponse(int httpStatusCode, String messageBundle, String messageKey, Object...inserts) throws IOException {
        ExceptionResponse er = new ExceptionResponse(messageKey, String.format(getMessageFormat(messageBundle, messageKey), inserts));
        return Response.status(httpStatusCode).entity(er).type(MediaType.APPLICATION_JSON).build();
    }
}
