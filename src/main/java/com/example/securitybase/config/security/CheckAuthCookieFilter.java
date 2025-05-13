package com.example.securitybase.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.config.HttpInfo;
import com.example.securitybase.config.RequestWrapper;
import com.example.securitybase.config.ResponseWrapper;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import com.example.securitybase.logging.common.MessageLog;
import com.example.securitybase.util.HttpUtil;
import com.example.securitybase.util.JsonConvertUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * kiểm tra request của người dùng trước khi nó tới controller
 *
 * @author
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CheckAuthCookieFilter extends OncePerRequestFilter {

    @Value("${server.servlet.contextPath}")
    private String contextPath;
    private static final ILogManage logManage = LogManage.getLogManage(CheckAuthCookieFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();

        if ((contextPath + "actuator/prometheus").equals(request.getRequestURI())
        ) {
            ThreadContext.put(MbConstants.UU_ID, uuid);
            ThreadContext.put(MbConstants.START_TIME, String.valueOf(start));
            ThreadContext.put(MbConstants.PATH, request.getRequestURI());

            filterChain.doFilter(request, response);

            ThreadContext.remove(MbConstants.UU_ID);
            ThreadContext.remove(MbConstants.START_TIME);
            ThreadContext.remove(MbConstants.PATH);
        } else {
            final RequestWrapper requestWrapper = new RequestWrapper(uuid, start, request);
            final ResponseWrapper responseWrapper = new ResponseWrapper(uuid, response);
            HttpInfo httpInfo = HttpUtil.getHttpInfo(requestWrapper);
            logRequest(requestWrapper, uuid, httpInfo);
            ThreadContext.put(MbConstants.UU_ID, uuid);
            ThreadContext.put(MbConstants.START_TIME, String.valueOf(start));
            ThreadContext.put(MbConstants.PATH, request.getRequestURI());

            filterChain.doFilter(requestWrapper, responseWrapper);
            ThreadContext.remove(MbConstants.UU_ID);
            ThreadContext.remove(MbConstants.START_TIME);
            ThreadContext.remove(MbConstants.PATH);
        }
    }

    private void logRequest(HttpServletRequest request, String uuid, HttpInfo httpInfo) {
// log request
        if (!"/cmvapi/actuator/health/liveness".equalsIgnoreCase(request.getRequestURI())
                && !"/cmvapi/actuator/health/readiness".equalsIgnoreCase(request.getRequestURI())) {
            MessageLog messageLog = new MessageLog();
            messageLog.setClassName(this.getClass().getName());
            messageLog.setMethodName(request.getMethod() + "|logRequest");
            messageLog.setId(uuid);
            messageLog.setPath(request.getRequestURI());
            try {
                messageLog.setPath(request.getRequestURI());
                Map messageMap = new HashMap();
                messageMap.put(MbConstants.BODY, JsonConvertUtil.convertObjectToJson(httpInfo));
                messageLog.setMessages(messageMap);

                logManage.info(messageLog);
            } catch (Exception e) {
                messageLog.setMessage(
                        String.format("logRequest %s - Error: %s", request.getMethod(), request.getRequestURI()));
                messageLog.setException(e);
                logManage.error(messageLog, new LogManage.Configuration(true, false));
            }
        }
    }
}
