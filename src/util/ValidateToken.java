package util;

import Define.KeywordDefine;

public class ValidateToken {
    /**
     * 校验authToken
     */
    public static Boolean isValid(String message) {
        assert message != null;
        String tokenValue = message.substring(message.indexOf(":") + 1);
        return tokenValue.equals(KeywordDefine.authToken);
    }
}
