package util;

import Define.KeywordDefine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser{


    public static Boolean MethodParser(String message) {
        assert message != null;
        return message.startsWith("GET") || message.startsWith("POST")
                || message.startsWith("PUT") || message.startsWith("DELETE");
    }

    public static Boolean tokenParser(String message) {
        assert message != null;
        return message.startsWith("authToken");
    }

    public static String responseParser(String message) {
        assert message != null;
        return message.substring(0, message.indexOf("originAddress:"));
    }

    public static String addressParser(String message) {
        assert message != null;
        Pattern pattern = Pattern.compile(Pattern.quote(KeywordDefine.originAddress_KEY) + "(.*?)" + Pattern.quote("\r"));
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
