package com.example.securitybase.exception;

import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import com.example.securitybase.logging.common.MessageLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * Override toString để convert object error to string
 * @author hieunt
 *
 */
public class BaseObject {
	protected final ILogManage logger = LogManage.getLogManage(getClass());

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error(new MessageLog().setMessage("BaseObject: ").setException(e));
			jsonString = "Can't build json from object";
		}
		return jsonString;
	}
}