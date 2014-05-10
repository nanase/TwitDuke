/*
 * The MIT License
 *
 * Copyright 2014 noko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.nokok.twitduke.core.type;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author noko
 */
public class ScreenNameTest {

    @Test(expected = NullPointerException.class)
    public void testNullConstractorArg() {
        new ScreenName(null);
    }

    @Test
    public void testGet() {
        String value = "hoge";
        ScreenName screenName = new ScreenName(value);
        assertTrue(screenName.get().equals(value));
    }

    @Test
    public void testEquals() {
        String value = "hoge";
        ScreenName screenName1 = new ScreenName(value);
        ScreenName screenName2 = new ScreenName(value);
        assertTrue(screenName1.equals(screenName2));
    }

    @Test
    public void testHashCode() {
        String value = "hoge";
        ScreenName screenName1 = new ScreenName(value);
        ScreenName screenName2 = new ScreenName(value);
        assertTrue(screenName1.hashCode() == screenName2.hashCode());
    }

    @Test
    public void testToString() {
        String value = "hoge";
        ScreenName screenName = new ScreenName(value);
        Assert.assertEquals(screenName.toString(), value);
    }
}
