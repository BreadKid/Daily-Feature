package com.baozun.bs.moncler.common.utils;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Base64;

public class JWTUtils {
    private static final int HEADER_IDX = 0;
    private static final int PAYLOAD_IDX = 1;
    private static final int SIGNATURE_IDX = 2;

    /**
     * 获取jwt指定部分
     * @param jwt
     * @param idx
     * @return
     */
    private static JsonObject getSpecPart(String jwt,int idx) {
        String[] parts = jwt.split("\\.");
        String partStr = parts[idx];
        byte[] partByte = Base64.getDecoder().decode(partStr);
        return new GsonBuilder().disableHtmlEscaping().create().fromJson(new String(partByte),JsonObject.class);
    }

    /**
     * 获取header
     * @param jwt
     * @return
     */
    private static JsonObject getHeader(String jwt) {
        return getSpecPart(jwt,HEADER_IDX);
    }

    /**
     * 获取payload
     * @param jwt
     * @return
     */
    private static JsonObject getData(String jwt) {
        return getSpecPart(jwt,PAYLOAD_IDX);
    }

    /**
     * 获取signature
     * @param jwt
     * @return
     */
    private static JsonObject getSignature(String jwt) {
        return getSpecPart(jwt,SIGNATURE_IDX);
    }

    /**
     * 获取payload模块中指定字段值
     * @param jwt
     * @param fieldK 字段key
     * @return
     */
    private static String getDataSpecField(String jwt, String fieldK) {
        JsonObject json = getData(jwt);
        if (!json.has(fieldK)) {
            return null;
        }
        return json.get(fieldK).getAsString();
    }

    /**
     * 获取jwt中账号
     * @param jwt
     * @return ACCOUNT_NO
     */
    public static String getAccountNo(String jwt) {
        try {
            String ACCOUNT_NO = "accountNo";
            return getDataSpecField(jwt,ACCOUNT_NO);
        } catch (Exception e) {
            return null;
        }
    }

}
