package com.breadykid.dailyfeature;

import com.breadykid.dailyfeature.util.DataMaskingUtil;
import org.junit.jupiter.api.Test;

/**
 * @description: 脱敏单测
 * @author: Joyce Liu
 */
public class DataMaskUtilTest {

    @Test
    public void testMaskNameZh1() {
        String r = DataMaskingUtil.maskNameZh("上官测试");
        assert "上官*试".equals(r);
    }
    @Test
    public void testMaskNameZh2() {
        String r = DataMaskingUtil.maskNameZh("王测试");
        assert "王*试".equals(r);
    }
    @Test
    public void testMaskNameZh3() {
        String r = DataMaskingUtil.maskNameZh("测试");
        assert "测*".equals(r);
    }

    @Test
    public void testMaskMobile() {
        String result = DataMaskingUtil.maskMobile("13088887777");
        assert "130****7777".equals(result);
    }

    @Test
    public void testIdNo1() {
        String r = DataMaskingUtil.maskIdNo("310108200310141234");
        assert "310108*********234".equals(r);
    }

    @Test
    public void testIdNo2() {
        String r = DataMaskingUtil.maskIdNo("31010820031014X");
        assert "310108******14X".equals(r);
    }

    @Test
    public void testMaskEmail() {
        String r = DataMaskingUtil.maskEmail("12345678909087@1.com");
        assert "1****@1.com".equals(r);
    }

}
