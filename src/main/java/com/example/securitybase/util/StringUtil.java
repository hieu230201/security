package com.example.securitybase.util;

import java.sql.Timestamp;
import java.text.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hieunt
 */
public class StringUtil {
    /***
     * Kiem tra chuoi co ky tu dac biet
     *
     * @param inputStr
     * @return boolean
     */
    public static boolean isContainSpecialCharacter(String inputStr) {

        if (inputStr == null) {
            return false;
        }
        inputStr = inputStr.replace(" ", "");
        if ("".equals(inputStr)) {
            return false;
        }

        Pattern p = Pattern.compile("[~!@#$%^&*()-=+_?><,|]");
        Matcher m = p.matcher(inputStr);

        return m.find();
    }

    public static String valueOfTimestamp(Date date, String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);

    }

    public static String replaceAllSpaces(String str, String replaceBy) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        return str.replaceAll("\\s+", replaceBy);
    }

    public static boolean isNullOrEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isNotNullAndEmpty(String str) {
        return (!(isNullOrEmpty(str)));
    }

    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }

    public static boolean equals(String str1, String str2) {
        return (str1 != null && (str2 == null || str1.equals(str2)));
    }

    public static boolean checkRegexStr(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean checkLength(String str, int min, int max) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        } else
            return str.length() >= min && str.length() <= max;
    }

    public static boolean checkMinLength(String str, int min) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        } else
            return str.length() >= min;
    }

    public static boolean checkMaxLength(String str, int max) {
        if ((str == null) || (str.length() == 0)) {
            return true;
        } else
            return str.length() <= max;
    }

    public static String replace(String value, String oldPart, String newPart) {
        if (value == null || value.length() == 0 || oldPart == null || oldPart.length() == 0)
            return value;
        //
        int oldPartLength = oldPart.length();
        String oldValue = value;
        StringBuilder retValue = new StringBuilder();
        int pos = oldValue.indexOf(oldPart);
        while (pos != -1) {
            retValue.append(oldValue, 0, pos);
            if (newPart != null && newPart.length() > 0)
                retValue.append(newPart);
            oldValue = oldValue.substring(pos + oldPartLength);
            pos = oldValue.indexOf(oldPart);
        }
        retValue.append(oldValue);
        return retValue.toString();
    }

    public static boolean isHasWhiteSpaceBeginEnd(String str) {
        if ((str == null) || (str.length() == 0))
            return false;
        return (str.endsWith(" ") || str.startsWith(" "));
    }

    public static boolean isHasWhiteSpace(String str) {
        if ((str == null) || (str.length() == 0))
            return false;
        return (str.contains(" "));
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    public static String valueOfTimestamp(Date timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String result = "";
        if (timestamp != null) {
            result = dateFormat.format(new Date(timestamp.getTime()));
        }
        return result;
    }

    public static String valueOfTimestamp(Timestamp timestamp, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(timestamp.getTime()));
    }

    public static String doubleFormat(double value) {
        String result = "";
        NumberFormat formatter = new DecimalFormat("#0.00");
        result = formatter.format(value);
        return result;
    }

    public static String doubleFormat(double value, String format) {
        String result = "";
        NumberFormat formatter = new DecimalFormat(format);
        result = formatter.format(value);
        return result;
    }

    public static String priceWithoutDecimal(double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String trim(String stringToTrim, String stringToRemove) {
        String answer = stringToTrim;

        while (answer.startsWith(stringToRemove)) {
            answer = answer.substring(stringToRemove.length());
        }

        while (answer.endsWith(stringToRemove)) {
            answer = answer.substring(0, answer.lastIndexOf(stringToRemove));
        }

        return answer;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s).filter(str -> str.length() != 0).map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }

    public static String stripAccents(String text) {
        return text == null ? null :
                Normalizer.normalize(text, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String lTrim(String text) {
        return text.replaceAll("^\\s+", "");
    }

    public static String rTrim(String text) {
        return text.replaceAll("\\s+$", "");
    }

    public static String convertText(String text) {
        String[] twoByte = {"ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ",
                "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ",
                "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ", "０", "１", "２", "３", "４", "５", "６", "７", "８", "９"};
        String[] oneByte = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        if (StringUtil.isNullOrEmpty(text)) {
            return "";
        }
        var result = stripAccents(lTrim(rTrim(formatSpacing(text))));
        var i = 0;
        for (i = 0; i < twoByte.length; i++) {
            result = result.replaceAll(twoByte[i], oneByte[i]);
        }
        return result == null ? null : convertVietNamese(convertSpecialCharacterVn(result));
    }

    public static String formatSpacing(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }


    public static String convertVietNamese(String str) {
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("đ", "d");

        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("(\\r|\\n)", "");
        return str;
    }

    public static String convertSpecialCharacterVn(String str) {
        str = str.replaceAll("à", "à");
        str = str.replaceAll("ạ", "ạ");
        str = str.replaceAll("á", "á");
        str = str.replaceAll("ã", "ã");
        str = str.replaceAll("ả", "ả");

        str = str.replaceAll("À", "À");
        str = str.replaceAll("Ạ", "Ạ");
        str = str.replaceAll("Á", "Á");
        str = str.replaceAll("Ã", "Ã");
        str = str.replaceAll("Ả", "Ả");

        str = str.replaceAll("ằ", "ằ");
        str = str.replaceAll("ặ", "ặ");
        str = str.replaceAll("ắ", "ắ");
        str = str.replaceAll("ẵ", "ẵ");
        str = str.replaceAll("ẳ", "ẳ");

        str = str.replaceAll("Ằ", "Ằ");
        str = str.replaceAll("Ặ", "Ặ");
        str = str.replaceAll("Ắ", "Ắ");
        str = str.replaceAll("Ẵ", "Ẵ");
        str = str.replaceAll("Ẳ", "Ẳ");

        str = str.replaceAll("ầ", "ầ");
        str = str.replaceAll("ậ", "ậ");
        str = str.replaceAll("ấ", "ấ");
        str = str.replaceAll("ẫ", "ẫ");
        str = str.replaceAll("ẩ", "ẩ");

        str = str.replaceAll("Ầ", "Ầ");
        str = str.replaceAll("Ậ", "Ậ");
        str = str.replaceAll("Ấ", "Ấ");
        str = str.replaceAll("Ẫ", "Ẫ");
        str = str.replaceAll("Ẩ", "Ẩ");

        str = str.replaceAll("è", "è");
        str = str.replaceAll("ẹ", "ẹ");
        str = str.replaceAll("é", "é");
        str = str.replaceAll("ẽ", "ẽ");
        str = str.replaceAll("ẻ", "ẻ");

        str = str.replaceAll("È", "È");
        str = str.replaceAll("Ẹ", "Ẹ");
        str = str.replaceAll("É", "É");
        str = str.replaceAll("Ẽ", "Ẽ");
        str = str.replaceAll("Ẻ", "Ẻ");

        str = str.replaceAll("ề", "ề");
        str = str.replaceAll("ệ", "ệ");
        str = str.replaceAll("ế", "ế");
        str = str.replaceAll("ễ", "ễ");
        str = str.replaceAll("ể", "ể");

        str = str.replaceAll("Ề", "Ề");
        str = str.replaceAll("Ệ", "Ệ");
        str = str.replaceAll("Ế", "Ế");
        str = str.replaceAll("Ễ", "Ễ");
        str = str.replaceAll("Ể", "Ể");

        str = str.replaceAll("ì", "ì");
        str = str.replaceAll("ị", "ị");
        str = str.replaceAll("í", "í");
        str = str.replaceAll("ĩ", "ĩ");
        str = str.replaceAll("ỉ", "ỉ");

        str = str.replaceAll("Ì", "Ì");
        str = str.replaceAll("Ị", "Ị");
        str = str.replaceAll("Í", "Í");
        str = str.replaceAll("Ĩ", "Ĩ");
        str = str.replaceAll("Ỉ", "Ỉ");

        str = str.replaceAll("Ð", "Đ");

        str = str.replaceAll("ò", "ò");
        str = str.replaceAll("ọ", "ọ");
        str = str.replaceAll("ó", "ó");
        str = str.replaceAll("õ", "õ");
        str = str.replaceAll("ỏ", "ỏ");

        str = str.replaceAll("Ò", "Ò");
        str = str.replaceAll("Ọ", "Ọ");
        str = str.replaceAll("Ó", "Ó");
        str = str.replaceAll("Õ", "Õ");
        str = str.replaceAll("Ỏ", "Ỏ");

        str = str.replaceAll("ờ", "ờ");
        str = str.replaceAll("ợ", "ợ");
        str = str.replaceAll("ớ", "ớ");
        str = str.replaceAll("ỡ", "ỡ");
        str = str.replaceAll("ở", "ở");

        str = str.replaceAll("Ờ̀", "Ờ");
        str = str.replaceAll("Ợ", "Ợ");
        str = str.replaceAll("Ớ", "Ớ");
        str = str.replaceAll("Ỡ", "Ỡ");
        str = str.replaceAll("Ở", "Ở");

        str = str.replaceAll("ồ", "ồ");
        str = str.replaceAll("ộ", "ộ");
        str = str.replaceAll("ố", "ố");
        str = str.replaceAll("ỗ", "ỗ");
        str = str.replaceAll("ổ", "ổ");

        str = str.replaceAll("Ồ", "Ồ");
        str = str.replaceAll("Ộ", "Ộ");
        str = str.replaceAll("Ố", "Ố");
        str = str.replaceAll("Ỗ", "Ỗ");
        str = str.replaceAll("Ổ", "Ổ");

        str = str.replaceAll("ù", "ù");
        str = str.replaceAll("ụ", "ụ");
        str = str.replaceAll("ú", "ú");
        str = str.replaceAll("ũ", "ũ");
        str = str.replaceAll("ủ", "ủ");

        str = str.replaceAll("Ù", "Ù");
        str = str.replaceAll("Ụ", "Ụ");
        str = str.replaceAll("Ú", "Ú");
        str = str.replaceAll("Ũ", "Ũ");
        str = str.replaceAll("Ủ", "Ủ");

        str = str.replaceAll("ừ", "ừ");
        str = str.replaceAll("ự", "ự");
        str = str.replaceAll("ứ", "ứ");
        str = str.replaceAll("ữ", "ữ");
        str = str.replaceAll("ử", "ử");

        str = str.replaceAll("Ừ", "Ừ");
        str = str.replaceAll("Ự", "Ự");
        str = str.replaceAll("Ứ", "Ứ");
        str = str.replaceAll("Ữ", "Ữ");
        str = str.replaceAll("Ử", "Ử");

        str = str.replaceAll("ỳ", "ỳ");
        str = str.replaceAll("ỵ", "ỵ");
        str = str.replaceAll("ý", "ý");
        str = str.replaceAll("ỹ", "ỹ");
        str = str.replaceAll("ỷ", "ỷ");

        str = str.replaceAll("Ỳ", "Ỳ");
        str = str.replaceAll("Ỵ", "Ỵ");
        str = str.replaceAll("Ý", "Ý");
        str = str.replaceAll("Ỹ", "Ỹ");
        str = str.replaceAll("Ỷ", "Ỷ");

        str = str.replaceAll("\u00AD|–", "-");
        str = str.replaceAll("…", "...");
        str = str.replaceAll("”", "\"");
        str = str.replaceAll("“", "\"");
        str = str.replaceAll("”", "\"");
        str = str.replaceAll("]", "]");
        str = str.replaceAll("→", "->");
        str = str.replaceAll(" ", " ");
        str = str.replaceAll("\u0002", " ");

        str = str.replaceAll("(\\r|\\n)", "");
        return str;
    }

    public static String convertTextThongTinHoSo(String text) {
        return text == null ? null : convertSpecialCharacterVn(text).replace("//", "/");
    }

    public static String subStringComma (String text) {
        if (isNotNullAndEmpty(text)) {
            text = text.trim();
            if (text.endsWith(",")) {
                return text.substring(0, text.length() - 1);
            }
        }
        return text;
    }

    public static String removeSpecialCharacter(String input) {
        if (isNotNullAndEmpty(input)) {
            return input.replaceAll("[\\p{S}\\p{P}º]+", " ");
        }
        return input;
    }

    public static String replaceAllSpecialUnicode(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.replaceAll("[^\\p{L}\\p{Nd}\\s,./:;!@#$%^&*(){}]+", " ");
    }

}
