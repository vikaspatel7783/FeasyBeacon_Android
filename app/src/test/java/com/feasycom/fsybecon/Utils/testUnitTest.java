package com.feasycom.fsybecon.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 32969 on 2017/8/31.
 */
public class testUnitTest {
    String a="12";
    byte []b=a.getBytes();
    @Test
    public void test1() throws Exception {
        System.out.println(b[0]);
    }

}