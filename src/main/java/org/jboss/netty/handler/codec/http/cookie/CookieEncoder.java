/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.handler.codec.http.cookie;

import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstInvalidCookieNameOctet;
import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstInvalidCookieValueOctet;
import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstWarnCookieValueOctet;
import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.unwrapValue;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

/**
 * Parent of Client and Server side cookie encoders
 */
public abstract class CookieEncoder {

    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(CookieEncoder.class);

    private final boolean strict;

    protected CookieEncoder(boolean strict) {
        this.strict = strict;
    }

    protected void validateCookie(String name, String value) {
        if (strict) {
            int pos;

            if ((pos = firstInvalidCookieNameOctet(name)) >= 0) {
                throw new IllegalArgumentException("Cookie name contains an invalid char: " + name.charAt(pos));
            }

            CharSequence unwrappedValue = unwrapValue(value);
            if (unwrappedValue == null) {
                throw new IllegalArgumentException("Cookie value wrapping quotes are not balanced: " + value);
            }

            if ((pos = firstInvalidCookieValueOctet(unwrappedValue)) >= 0) {
                throw new IllegalArgumentException("Cookie value contains an invalid char: " + value.charAt(pos));
            }

            if ((pos = firstWarnCookieValueOctet(unwrappedValue)) >= 0) {
                String warning = String.format("cookie with name '%s' has invalid characters", name);
                logger.warn(warning);
            }
        }
    }
}
