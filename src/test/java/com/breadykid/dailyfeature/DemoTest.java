package com.breadykid.dailyfeature;

import org.junit.jupiter.api.Test;

/**
 * @description: 工具单测
 * @author: Joyce Liu
 */
public class DemoTest {

    @Test
    public void stringFormatCase () {
        String str = "a";
        //原值
        String lowerCase = String.format("%s",str);
        //转大写
        String upperCase = String.format("%S",str);
        assert !lowerCase.equals(upperCase);
        assert lowerCase.equalsIgnoreCase(upperCase);
    }
}
