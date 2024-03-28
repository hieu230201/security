package com.example.securitybase.util;


import com.example.securitybase.config.HttpInfo;
import com.example.securitybase.config.RequestWrapper;
import com.example.securitybase.config.ResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
	
	public static HttpInfo getHttpInfo(RequestWrapper requestWrapper, ResponseWrapper responseWrapper)
			throws UnsupportedEncodingException {
		HttpInfo httpInfo = new HttpInfo();
		httpInfo.setUrl(getUrl(requestWrapper));
		httpInfo.setRequestHeaders(getRequestHeaders(requestWrapper));
		httpInfo.setRequestBody(getRequestBody(requestWrapper));
		httpInfo.setAuthorization(getRequestHeaders(requestWrapper).get("authorization"));
		httpInfo.setResponseHeaders(getResponseHeaders(responseWrapper));
		httpInfo.setResponseBody(getResponseBody(responseWrapper));
		httpInfo.setStatus(getHttpStatus(responseWrapper));

		return httpInfo;
	}

	public static  HttpInfo getHttpInfo(RequestWrapper requestWrapper) throws UnsupportedEncodingException {
		HttpInfo httpInfo = new HttpInfo();
		httpInfo.setUrl(getUrl(requestWrapper));
		httpInfo.setRequestHeaders(getRequestHeaders(requestWrapper));
		httpInfo.setRequestBody(getRequestBody(requestWrapper));
		return httpInfo;
	}

	public static Map<String, String> getRequestHeaders(RequestWrapper requestWrapper) {
		Map<String, String> headers = new HashMap<>();
		Collections.list(requestWrapper.getHeaderNames())
				.forEach(key -> headers.put((String) key, requestWrapper.getHeader((String) key)));
		return headers;
	}

	public static Map<String, String> getResponseHeaders(ResponseWrapper responseWrapper) {
		Map<String, String> headers = new HashMap<>();
		responseWrapper.getHeaderNames().forEach(key -> headers.put(key, responseWrapper.getHeader(key)));
		return headers;
	}

	public static String getUrl(RequestWrapper requestWrapper) {
		return requestWrapper.getRequestURL() + "?" + requestWrapper.getQueryString();
	}

	public static String getRequestBody(RequestWrapper requestWrapper) throws UnsupportedEncodingException {
		return requestWrapper.getBody();
	}

	public static String getResponseBody(ResponseWrapper responseWrapper) throws UnsupportedEncodingException {
		return new String(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding());
	}

	private static int getHttpStatus(ResponseWrapper responseWrapper) throws UnsupportedEncodingException {
		return responseWrapper.getStatus();
	}
}