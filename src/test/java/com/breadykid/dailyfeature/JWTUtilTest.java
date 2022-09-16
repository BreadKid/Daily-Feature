package com.baozun.bs.moncler.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class JWTUtilTest {

    @Test
    public void test0() {
        String mockToken = "eyJUeXBlIjoiSnd0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJhY2NvdW50TmFtZSI6IjE1OTIxMDg3NzA0Iiwic2Fhc1RlbmFudENvZGUiOiI4ODAwNDAwNiIsImFjY291bnRObyI6IjIzMDcwNTc1NDE4NDg4NDIyNiIsImV4cCI6MTYzNTY0NTU5Niwic3RvcmVDb2RlIjoiIn0.tHVOpUmtDvBHgiA-CecwXqpKIdvAPieyiUzExXd8G7A";
        String a = JWTUtils.getAccountNo(mockToken);
        Assert.assertEquals("230705754184884226",a);
    }

    @Test
    public void test1() {
        String mockToken = "1.1.1";
        String a = JWTUtils.getAccountNo(mockToken);
        Assert.assertNull(a);
    }

    @Test
    public void test2() {
        String mockToken = "123456";
        String a = JWTUtils.getAccountNo(mockToken);
        Assert.assertNull(a);
    }

    @Test
    public void test3() {
        String mockToken = null;
        String a = JWTUtils.getAccountNo(mockToken);
        Assert.assertNull(a);
    }

    @Test
    public void test4() {
        String mockToken = "eyJUeXBlIjoiSnd0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJhY2NvdW50TmFtZSI6IjE1OTIxMDg3NzA0Iiwic2Fhc1RlbmFudENvZGUiOiI4ODAwNDAwNiIsImFjY291bnRObyI6IjIzMDcwNTc1NDE4NDg4NDIyNiIsImV4cCI6MTYzNTY0NTU5Niwic3RvcmVDb2RlIjoiIn0";
        String a = JWTUtils.getAccountNo(mockToken);
        Assert.assertEquals("230705754184884226",a);
    }
}
