package util;

import define.KeywordDefine;

public class ValidateToken {
    /**
     * get the token value from the message and check if the message is valid
     * @param message the message to be checked
     * @return true if the message is valid, false otherwise
     */
    public static Boolean isValid(String message) {
        assert message != null;
        String tokenValue = message.substring(message.indexOf(":") + 1,message.indexOf("\r\n"));
        return tokenValue.equals(KeywordDefine.authToken);
    }

}
