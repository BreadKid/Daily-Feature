package com.breadykid.dailyfeature;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @description: StringFormat使用
 * @author: Joyce Liu
 */
public class StringFormatTest {

    @Test
    public void stringFormatCase () {
        String str = "abc";
        //原值
        String originalCase = String.format("🌰:%s",str);
        //转大写
        String upperCase = String.format("🌰:%S",str);
        assert !originalCase.equals(upperCase);
        assert originalCase.equalsIgnoreCase(upperCase);
    }

    @Test
    public void stringFormatTime() {
        LocalDateTime now = LocalDateTime.now();
        String nowTime = String.format("🌰:%tc",now);
        assert true;
    }

    @Test
    public void stringFormatChar() {
        String str = String.format("🌰:%c",'a');

        assert !"".equals(str);
    }

    @Test
    public void stringFormatDouble() {
        String str = String.format("🌰:%d",'a');

        assert !"".equals(str);
    }
}
