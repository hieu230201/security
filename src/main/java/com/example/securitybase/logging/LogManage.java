package com.example.securitybase.logging;

import java.util.Date;

import com.example.securitybase.comon.enums.LogLevel;
import com.example.securitybase.logging.bases.ILogManage;
import com.example.securitybase.logging.common.MessageLog;
import com.example.securitybase.logging.common.MessageLogKafka;
import com.example.securitybase.util.BeanUtil;
import com.example.securitybase.util.JsonConvertUtil;
import com.example.securitybase.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class LogManage implements ILogManage {
	public static class Configuration {
		private Boolean useLogFile = true;
		private Boolean useKibanaLog = false;
		private Boolean useKafkaLog = true;

		public Configuration() {
		}

		public Configuration(Boolean useLogFile, Boolean useKafkaLog) {
			this.useKafkaLog = useKafkaLog;
			this.useLogFile = useLogFile;
		}

		public Boolean getUseLogFile() {
			return useLogFile;
		}

		public void setUseLogFile(Boolean useLogFile) {
			this.useLogFile = useLogFile;
		}

		public Boolean getUseKibanaLog() {
			return useKibanaLog;
		}

		public void setUseKibanaLog(Boolean useKibanaLog) {
			this.useKibanaLog = useKibanaLog;
		}

		public Boolean getUseKafkaLog() {
			return useKafkaLog;
		}

		public void setUseKafkaLog(Boolean useKafkaLog) {
			this.useKafkaLog = useKafkaLog;
		}
	}

	interface ActionLog {
		void run(Logger logger);
	}

	public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private final Logger logger;

	public static ILogManage getLogManage(Class<?> clazz) {
		return new LogManage(clazz);
	}

	public LogManage(Class<?> clazz) {

		logger = Logger.getLogger(clazz);
	}

	private boolean log(ActionLog actionLog, String logLevel, MessageLog messageLog, Configuration configuration) {
		configuration = configuration == null ? new Configuration() : configuration;

		if (messageLog == null)
			return false;
		if (StringUtils.isBlank(messageLog.getClassName()))
			messageLog.setClassName(logger.getName());

		if (configuration.useLogFile) {
			actionLog.run(logger);
		}
		if (configuration.useKibanaLog) {

		}
//		if (configuration.useKafkaLog) {
//			KafkaLog kafkaLog = BeanUtil.getBean(KafkaLog.class);
//
//			MessageLogKafka messageLogKafka = new MessageLogKafka();
//			messageLogKafka.setTimestamp(StringUtil.valueOfTimestamp(new Date(), PATTERN));
//			messageLogKafka.setLevel(logLevel);
//			messageLogKafka.setMethod(messageLog.getMethodName());
//			messageLogKafka.setServicemessageid(messageLog.getId());
//			messageLogKafka.setFulldata(JsonConvertUtil.convertObjectToJson(messageLog));
//			messageLogKafka.setServicename(messageLog.getClassName());
//			messageLogKafka.setDuration(messageLog.getDuration());
//			messageLogKafka.setPage(messageLog.getPath());
//			String message = JsonConvertUtil.convertObjectToJson(messageLogKafka);
//			kafkaLog.sendMessage(message);
//		}

		return true;
	}

	@Override
	public boolean debug(String message) {
		return debug(new MessageLog().setMessage(message));
	}

	public boolean debug(MessageLog messageLog) {
		return debug(messageLog, null);
	}

	public boolean debug(MessageLog messageLog, Configuration configuration) {
		return log(x -> x.debug(messageLog, messageLog.getException()), LogLevel.DEBUG.name(), messageLog,
				configuration);
	}

	@Override
	public boolean trace(String message) {
		return trace(new MessageLog().setMessage(message));
	}

	public boolean trace(MessageLog messageLog) {
		return debug(messageLog, null);
	}

	public boolean trace(MessageLog messageLog, Configuration configuration) {
		return log(x -> x.trace(messageLog, messageLog.getException()), LogLevel.TRACE.name(), messageLog,
				configuration);
	}

	@Override
	public boolean info(String message) {
		return info(new MessageLog().setMessage(message));
	}

	public boolean info(MessageLog messageLog) {
		return info(messageLog, null);
	}

	public boolean info(MessageLog messageLog, Configuration configuration) {
		return log(x -> x.info(messageLog, messageLog.getException()), LogLevel.INFO.name(), messageLog, configuration);
	}

	@Override
	public boolean error(String message, Exception e) {
		return error(new MessageLog().setMessage(message).setException(e));
	}

	public boolean error(MessageLog messageLog) {
		return error(messageLog, null);
	}

	public boolean error(MessageLog messageLog, Configuration configuration) {
		return log(x -> x.error(messageLog, messageLog.getException()), LogLevel.ERROR.name(), messageLog,
				configuration);
	}

	@Override
	public boolean fatal(String message, Exception e) {
		return fatal(new MessageLog().setMessage(message).setException(e));
	}

	public boolean fatal(MessageLog messageLog) {
		return fatal(messageLog, null);
	}

	public boolean fatal(MessageLog messageLog, Configuration configuration) {
		return log(x -> x.fatal(messageLog, messageLog.getException()), LogLevel.FATAL.name(), messageLog,
				configuration);
	}

}