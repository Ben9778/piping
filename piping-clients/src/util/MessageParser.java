package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    private static final String serverHostName="Host: localhost:12345";
    private static final String localHostName="Host: localhost:";
    private static final String hostAddress="hostAddress:";

    public static Boolean MethodParser(String message){
        assert message != null;
        return message.startsWith("GET")||message.startsWith("POST")||
                message.startsWith("PUT")||message.startsWith("DELETE");
    }

    public static String parserHostAddress(String message){
        assert message != null;
        Pattern pattern = Pattern.compile(Pattern.quote("hostAddress:") + "(.*?)" + Pattern.quote("\r"));
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String replaceHost(String message,int port){
        assert message != null;
        return message.replace(serverHostName,localHostName+port);
    }

    public static String getNewResponse(String message){
        assert message != null;
        return message.substring(0,message.indexOf(hostAddress));
    }
}
