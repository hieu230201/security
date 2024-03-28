package com.example.securitybase.util;

import com.example.securitybase.config.RequestWrapper;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;
import com.example.securitybase.model.BodyToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final ILogManage logger = LogManage.getLogManage(CommonUtil.class);

    public static Object getDefaultValue(Class<?> clazz) {
        if (Boolean.class == clazz || boolean.class == clazz) {
            return Boolean.FALSE;
        } else if (Integer.class == clazz || int.class == clazz) {
            return 0;
        } else if (Long.class == clazz || long.class == clazz) {
            return 0L;
        } else if (Float.class == clazz || float.class == clazz) {
            return 0f;
        } else if (Double.class == clazz || double.class == clazz) {
            return 0d;
        } else if (String.class == clazz) {
            return "";
        } else if (List.class == clazz) {
            return new ArrayList<>();
        }
        return null;
    }
    public static Boolean isPrimitiveTypeOrWrapper(Class<?> clazz){
        return BeanUtils.isSimpleValueType(clazz);
    }

    public static long getLong(Object value) {
        return getLong(value, -1);
    }

    public static long getLong(Object value, long defaultValue) {
        if (value == null)
            return defaultValue;

        if (value instanceof Integer)
            return ((Integer) value).longValue();
        if (value instanceof Long)
            return ((Long) value).longValue();
        if (value instanceof Double)
            return ((Double) value).longValue();
        if (value instanceof BigDecimal)
            return ((BigDecimal) value).longValue();

        try {
            Double ii = Double.parseDouble(value.toString());
            return ii.longValue();
        } catch (NumberFormatException ex) {
            logger.error("getLong - value: " + ex.getMessage(), ex);
        }

        return defaultValue;
    }



    public static Double getDouble(Object obj, Double defaultValue) {
        if (obj != null) {
            return Double.parseDouble(obj.toString());
        }
        return defaultValue;
    }

    public static Object getValueByFieldName(Object entity, String fieldName) {
        try {
            if (entity != null) {
                java.lang.reflect.Field[] lstFiel = entity.getClass().getDeclaredFields();
                for (java.lang.reflect.Field field : lstFiel) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    try {
                        if (value != null) {
                            if (field.getName().equals(fieldName))
                                return value;
                        }
                    } catch (IllegalArgumentException e) {
                        logger.error("getValueByFieldName - fieldName: " + e.getMessage(), e);
                        return null;
                    }
                }
            } else
                return null;
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            logger.error("getValueByFieldName - fieldName: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    public static Object setFieldValueToObjByFieldName(Object entity, Object value, String fieldName) {
        try {
            if (entity != null && value != null && StringUtil.isNotNullAndEmpty(fieldName)) {
                java.lang.reflect.Field[] lstFiel = entity.getClass().getDeclaredFields();
                for (java.lang.reflect.Field field : lstFiel) {
                    field.setAccessible(true); // You might want to set modifier to public first.
                    if (field.getName().equals(fieldName)) {
                        if (field != null)
                            field.set(entity, value);
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            logger.error("setFieldValueToObjByFieldName - fieldName: " + e.getMessage(), e);
        }
        return entity;
    }

    public static BodyToken decodeJWTToken(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty())
            return null;
        logger.info("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];

        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        logger.info("JWT Body : " + body);
        return BodyToken.loadMeFromJsonString(body);
    }

    /**
     * Mask HTML content. i.e. replace characters with &values; CR is not masked
     *
     * @param content content
     * @return masked content
     * @see #maskHTML(String, boolean)
     */
    public static String maskHTML(String content) {
        return maskHTML(content, false);
    }

    /**
     * Mask HTML content. i.e. replace characters with &values;
     *
     * @param content content
     * @param maskCR  convert CR into <br>
     * @return masked content or null if the <code>content</code> is null
     */
    public static String maskHTML(String content, boolean maskCR) {
        // If the content is null, then return null - teo_sarca [ 1748346 ]
        if (content == null)
            return content;
        //
        StringBuilder out = new StringBuilder();
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
                case '<':
                    out.append("&lt;");
                    break;
                case '>':
                    out.append("&gt;");
                    break;
                case '&':
                    out.append("&amp;");
                    break;
                case '"':
                    out.append("&quot;");
                    break;
                case '\'':
                    out.append("&#039;");
                    break;
                case '\n':
                    if (maskCR)
                        out.append("<br>");
                    //
                default:
                    if ((int) c > 255) // Write Unicode
                        out.append("&#").append((int) c).append(";");
                    else
                        out.append(c);
                    break;
            }
        }
        return out.toString();
    }

    public static int findIndexOf(String str, String search) {
        if (str == null || search == null || search.length() == 0)
            return -1;
        //
        int endIndex = -1;
        int parCount = 0;
        boolean ignoringText = false;
        int size = str.length();
        while (++endIndex < size) {
            char c = str.charAt(endIndex);
            if (c == '\'')
                ignoringText = !ignoringText;
            else if (!ignoringText) {
                if (parCount == 0 && c == search.charAt(0)) {
                    if (str.substring(endIndex).startsWith(search))
                        return endIndex;
                } else if (c == ')')
                    parCount--;
                else if (c == '(')
                    parCount++;
            }
        }
        return -1;
    }

    /*
     * Remove CR / LF from String
     *
     * @param in input
     *
     * @return cleaned string
     */
    public static String removeCRLF(String in) {
        char[] inArray = in.toCharArray();
        StringBuilder out = new StringBuilder(inArray.length);
        for (int i = 0; i < inArray.length; i++) {
            char c = inArray[i];
            if (c == '\n' || c == '\r')
                ;
            else
                out.append(c);
        }
        return out.toString();
    }

    public static byte[] convertObjectToByteArray(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception var4) {
            return null;
        }
    }

    public static Object convertByteArrayToObject(byte[] buf) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
            Object object = ois.readObject();
            ois.close();
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    public static String convertClobToString(Clob clob) {
        try {
            if (clob == null)
                return null;

            Reader reader = null;
            try {
                reader = clob.getCharacterStream();
                if (reader == null)
                    return null;
                char[] buffer = new char[(int) clob.length()];
                if (buffer.length == 0)
                    return null;
                reader.read(buffer);
                return new String(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException ignore) {
                        //throw new RuntimeException(e);
                    }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> GetKeyValuesByStringParameter(String stringQuery) {
        Map<String, String> map = new HashMap<>();
        try {
            if (StringUtil.isNullOrEmpty(stringQuery))
                return map;
            Pattern p = Pattern.compile("([^&?=]+)=([^&]+)", 2);
            Matcher match = p.matcher(stringQuery);

            while (match.find()) {
                map.put(match.group(1), match.group(2));
            }
        } catch (Exception e) {
            logger.error("GetKeyValuesByStringParameter error : " + e.getMessage(), e);
        }
        return map;
    }

//    public static ParameterModel getParameter(HttpServletRequest httpServletRequest) {
//        ParameterModel parameterModel = new ParameterModel();
//        try {
//            if (httpServletRequest instanceof SecurityContextHolderAwareRequestWrapper) {
//                SecurityContextHolderAwareRequestWrapper awarRequestWrapper = (SecurityContextHolderAwareRequestWrapper) httpServletRequest;
//                if (awarRequestWrapper.getRequest() instanceof RequestWrapper) {
//                    RequestWrapper requestWrapper = (RequestWrapper) awarRequestWrapper.getRequest();
//                    parameterModel.setUuid(requestWrapper.getUuid());
//                }
//            }
//            String queryUrl = httpServletRequest.getHeader("QueryUrl");
//            getParameterModelBpm(parameterModel, queryUrl);
//        } catch (Exception e) {
//            logger.error("getParameter error : " + e.getMessage(), e);
//        }
//        return parameterModel;
//    }

    /**
     * Kiểm tra list rỗng
     *
     * @param collection list
     * @return nếu rỗng trả về true
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }



//    public static ParameterModel getParameter(String bpmId) {
//        ParameterModel parameterModel = new ParameterModel();
//        try {
//
//            String queryUrl = bpmId;
//            getParameterModelBpm(parameterModel, queryUrl);
//        } catch (Exception e) {
//            logger.error("getParameter error : " + e.getMessage(), e);
//        }
//        return parameterModel;
//    }
//
//    private static void getParameterModelBpm(ParameterModel parameterModel, String queryUrl) throws Exception {
//        if (queryUrl != null) {
//            parameterModel.setRaw(queryUrl);
//            SecurityUtil.BpmDESEncryption desEncryption = new SecurityUtil.BpmDESEncryption();
//            String queryUrlDecode = desEncryption.decrypt(queryUrl.replace("%3D", "=").replace("%2b", "%2B").replace("%2f", "%2F").replace("%2B", "+").replace("%2F", "/").replace("%20", "+"));
//            Map<String, String> paramMap = CommonUtil.GetKeyValuesByStringParameter(queryUrlDecode);
//
//            if (paramMap != null && !paramMap.isEmpty()) {
//                for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
//                    CommonUtil.setFieldValueToObjByFieldName(parameterModel, entry.getValue(), entry.getKey());
//                }
//
//            }
////                if (StringUtil.isNotNullAndEmpty(parameterModel.getStatusId())) {
////                    String reportType = LinkID.valueOf(Integer.parseInt(parameterModel.getStatusId())).getReportType()
////                            .name();
////                    parameterModel.setReportType(reportType);
////                }
//        }
//    }


}