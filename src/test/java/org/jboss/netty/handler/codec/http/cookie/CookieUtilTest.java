package org.jboss.netty.handler.codec.http.cookie;

import org.junit.Test;

import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstWarnCookieValueOctet;
import static org.junit.Assert.assertEquals;

public class CookieUtilTest {

    @Test
    public void testNoWarningOnInvalidCharacters() {
        String cookieValue = "ball";
        int expected = -1;

        int actual = firstWarnCookieValueOctet(cookieValue);
        assertEquals(expected, actual);
    }

    @Test
    public void testWarningOnInvalidCharacters() {
        String cookieValue = "bal!l";
        int expected = 3;

        int actual = firstWarnCookieValueOctet(cookieValue);
        assertEquals(expected, actual);
    }
}
