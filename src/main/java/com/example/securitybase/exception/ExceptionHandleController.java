package com.example.securitybase.exception;

import com.example.securitybase.comon.ErrorCode;
import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.config.RequestWrapper;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import com.example.securitybase.logging.common.MessageLog;
import com.example.securitybase.response.ResponseData;
import com.example.securitybase.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Handler lại Response Exception trước khi trả client
 *
 * @author namkaka
 */
@ControllerAdvice
@Order(1)
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    private static final ILogManage logger = LogManage.getLogManage(ExceptionHandleController.class);
    @Autowired
    private MessageSource messageSource;
    private String uuid = null;
    private Long startTime = 0L;
    private String requestURI = null;

    private void initData(HttpServletRequest httpServletRequest) {
        uuid = ThreadContext.get(MbConstants.UU_ID);
        startTime = (!StringUtil.isNotNullAndEmpty(ThreadContext.get(MbConstants.START_TIME))) ? 0l : Long.parseLong(ThreadContext.get(MbConstants.START_TIME));
        requestURI = httpServletRequest.getRequestURI();
    }

    public final ResponseEntity<Object> handleAllExceptionImpl(Exception ex, HttpHeaders headers, HttpStatus status,
                                                               HttpServletRequest request) {

        try {
            initData(request);
        } catch (Exception e) {
        }

        MessageLog messageLog = new MessageLog();
        Map<String, String> messageMap = new HashMap<>();
        messageLog.setClassName(this.getClass().getName());
        if (request != null && request.getMethod() != null) {
            messageLog.setMethodName(request.getMethod() + "|handleAllExceptionImpl");
        } else {
            messageLog.setMethodName("ANY|handleAllExceptionImpl");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (requestURI == null) {
            requestURI = "ANY";
        }
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }

        messageLog.setId(uuid);
        messageLog.setPath(requestURI);
        if (startTime != 0)
            messageLog.setDuration((System.currentTimeMillis() - startTime));

        if (ex instanceof HttpStatusCodeException) {
            HttpStatusCodeException exception = (HttpStatusCodeException) ex;
            String message = exception.getResponseBodyAsString();
            return new ResponseEntity<>(new ResponseData<>().error(ErrorCode.HTTP_STATUS_CODE_EXCEPTION.getCode(),
                    getMessage(message, "", null), uuid), ErrorCode.HTTP_STATUS_CODE_EXCEPTION.getHttpStatus());
            //
        } else if (ex instanceof CustomServiceBusinessException) {
            CustomServiceBusinessException e = (CustomServiceBusinessException) ex;
            messageMap.put(MbConstants.PATH, request.getRequestURL().toString());
            messageMap.put(MbConstants.UU_ID, uuid);
            messageMap.put(MbConstants.MESSAGE_CODE, String.format("%s", e.getCode()));
            messageMap.put(MbConstants.ERROR_DETAILS, e.getMessage());
            messageLog.setMessages(messageMap);
            logger.info(messageLog);

            return new ResponseEntity<>(new ResponseData<>().error(e.getCode(),
                    getMessage(e.getDetailMessage(), e.getMessage(), e.getArgs()), null, null, null, getMessage(e.getDetailMessage(), e.getMessage(), e.getArgs())), e.getHttpStatus());
            //
        } else if (ex instanceof MethodArgumentNotValidException) {
            List<SubError> lstSubErr = new ArrayList<>();
            if (((MethodArgumentNotValidException) ex).getBindingResult() != null) {
                List<FieldError> fieldError = ((MethodArgumentNotValidException) ex).getBindingResult()
                        .getFieldErrors();
                for (FieldError error : fieldError) {
                    SubError subError = new SubError(error.getField(), error.getRejectedValue(),
                            error.getDefaultMessage());
                    lstSubErr.add(subError);
                }
            }

            return new ResponseEntity<>(new ResponseData<>().error(ErrorCode.UNPROCESSABLE_ENTITY.getCode(),
                    getMessage("", "", null), lstSubErr), ErrorCode.UNPROCESSABLE_ENTITY.getHttpStatus());
            //
        } else if (ex instanceof AccessDeniedException) {
            return new ResponseEntity<>(new ResponseData<>().error(ErrorCode.NO_RIGHTS_EXECUTE.getCode(),
                    getMessage("", "no.rights.execute", null), uuid), HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            if (request != null && request.getRequestURL() != null) {
                messageMap.put(MbConstants.PATH, request.getRequestURL().toString());
            } else {
                messageMap.put(MbConstants.PATH, "ANY");
            }
            messageMap.put(MbConstants.UU_ID, uuid);
            messageMap.put(MbConstants.MESSAGE, ExceptionUtils.getMessage(ex));
            messageMap.put(MbConstants.ERROR_DETAILS, ExceptionUtils.getFullStackTrace(ex));
            messageLog.setMessages(messageMap);
//            messageLog.setException(ex);
            try {
                logger.error(messageLog);
            } catch (Exception e) {
            }

            //trường hợp exception bị dài quá ko bắn lên đc kafka
            try {
                MessageLog messageLogEx = new MessageLog();
                Map<String, String> messageMapEx = new HashMap<>();
                messageLogEx.setClassName(this.getClass().getName());
                if (request != null && request.getMethod() != null)
                    messageLogEx.setMethodName(request.getMethod() + "|" + MbConstants.ERROR_DETAILS_MS016);
                String randomUUID = UUID.randomUUID().toString();
                messageLogEx.setId(randomUUID);
                messageLogEx.setPath(requestURI);
                messageLogEx.setClassName(MbConstants.ERROR_DETAILS_MS016);
                messageMapEx.put(MbConstants.UU_ID, randomUUID);
                messageMapEx.put(MbConstants.MESSAGE_CODE, String.format("%s", "900"));
                messageMapEx.put(MbConstants.ERROR_DETAILS, StringUtils.left(ExceptionUtils.getStackTrace(ex), 13999));
                messageLogEx.setMessages(messageMapEx);
                logger.info(messageLogEx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(new ResponseData<>().error(status.value(), getMsgResponse(), null), status);

    }

    private String getMsgResponse() {
        if (StringUtil.isNotNullAndEmpty(uuid))
            return String.format("Có lỗi trong quá trình xử lý%sVui lòng gửi mã sau cho bộ phận IT để được hỗ trợ%s %s",
                    System.lineSeparator(), System.lineSeparator(), uuid);
        else
            return "Có lỗi trong quá trình xử lý";
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, RequestWrapper request) {
        return handleAllExceptionImpl(ex, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String getMessage(String detailMessage, String code, Object[] args) {
        if (!StringUtil.isNullOrEmpty(detailMessage)) {
            return detailMessage;
        }
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }

}

