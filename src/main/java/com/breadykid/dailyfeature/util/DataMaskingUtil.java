package com.breadykid.dailyfeature.util;

/**
 * @description: 数据脱敏工具类
 * @author: Joyce Liu
 * 脱敏策略参考文档：
 * http://www.gjbmj.gov.cn/n1/2022/0321/c411145-32380157.html
 * https://tech.meituan.com/2014/04/08/data-desensitization.html
 *
 */
public class DataMaskingUtil {

    /**
     * 中文姓名脱敏
     * 测试->测*
     * 王测试->王*试
     * 上官测试->上官*试
     * @param nameZh 原数据
     * @return 脱敏后中文名
     */
    public static String maskNameZh(String nameZh) {
        if (nameZh==null || "".equals(nameZh) || nameZh.length()==1) {
            return nameZh;
        }
        StringBuilder str = new StringBuilder(nameZh);
        if (str.length()==2) {
            str.replace(str.length()-1,str.length(),"*");
            return str.toString();
        }
        str.replace(str.length()-2,str.length()-1,"*");
        return str.toString();
    }

    /**
     * 手机号脱敏
     * 13922224444->139****4444
     * @param mobile 原数据
     * @return 脱敏后手机号
     */
    public static String maskMobile(String mobile) {
        if (null==mobile || "".equals(mobile) || mobile.length()!=11) {
            return mobile;
        }
        return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

    /**
     * 邮箱脱敏
     * @param email 原数据
     * @return 脱敏后邮箱
     */
    public static String maskEmail(String email) {
        if (null==email || "".equals(email)) {
            return email;
        }
        return email.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
    }

    /**
     * 身份证脱敏
     * @param idNo
     * @return
     */
    public static String maskIdNo(String idNo) {
        if (null==idNo || "".equals(idNo) || (idNo.length()!=15&&idNo.length()!=18)) {
            return idNo;
        }
        if (idNo.length() == 15){
            return idNo.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
        }
        return idNo.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
    }
}
