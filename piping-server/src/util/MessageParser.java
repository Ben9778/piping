package util;

import define.KeywordDefine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to parse the message received from the client.
 */
public class MessageParser{

    // Check if the message contains a method
    public static Boolean MethodParser(String message) {
        assert message != null;
        return message.startsWith("GET") || message.startsWith("POST")
                || message.startsWith("PUT") || message.startsWith("DELETE");
    }

    // Check if the message contains a token
    public static Boolean tokenParser(String message) {
        assert message != null;
        return message.startsWith("authToken");
    }

    // parse the message and get the response message
    public static String responseParser(String message) {
        assert message != null;
        return message.substring(0, message.indexOf("originAddress:"));
    }
    // parse the message and get the origin address
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
