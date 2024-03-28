package com.example.securitybase.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.logging.common.MessageLog;
import com.example.securitybase.util.JsonConvertUtil;
import com.example.securitybase.util.StringUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
/**
 *
 * @author hieunt
 */
@ControllerAdvice
public class ResponseAdviceAdapter implements ResponseBodyAdvice {
    private static final ILogManage logManage = LogManage.getLogManage(ResponseAdviceAdapter.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
            String meThodName = null;
            if (returnType != null) {
                Method method = returnType.getMethod();
                if (method != null)
                    meThodName = method.getName();
            }
            if (MediaType.APPLICATION_JSON.equalsTypeAndSubtype(selectedContentType))
                logResponse(((ServletServerHttpRequest) request).getServletRequest(), body, meThodName);
        }

        return body;
    }

    public void logResponse(HttpServletRequest httpServletRequest, Object body, String meThodName) {
        MessageLog messageLog = new MessageLog();
        messageLog.setClassName(this.getClass().getName());
        messageLog.setMethodName(meThodName);
        messageLog.setPath(httpServletRequest.getRequestURI());
        Long startTime = 0L;
        String uuid = ThreadContext.get(MbConstants.UU_ID);
        try {
            String times = ThreadContext.get(MbConstants.START_TIME);
            if (StringUtil.isNotNullAndEmpty(times))
                startTime = Long.parseLong(times);
            Map messageMap = new HashMap();
            messageMap.put(MbConstants.FULL_RESPONSE, "Full log Response " + meThodName);
            messageMap.put(MbConstants.BODY, JsonConvertUtil.convertObjectToJson(body));

            messageLog.setMessages(messageMap);
            messageLog.setId(uuid);
            if (startTime != 0)
                messageLog.setDuration(System.currentTimeMillis() - startTime);
            logManage.info(messageLog);
        } catch (Exception ex) {
            messageLog.setId(uuid);
            messageLog.setMessage("logResponse - Error: " + ex.getMessage());
            messageLog.setException(ex);
            if (startTime != 0)
                messageLog.setDuration(System.currentTimeMillis() - startTime);

            logManage.error(messageLog, new LogManage.Configuration(true, false));
        }

    }

}
