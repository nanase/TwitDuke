/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ツイート(String)のラッパクラスです
 * <p>
 */
public class Tweet implements Cloneable, Serializable, Retrievable<String> {

    private static final long serialVersionUID = -669020520967130965L;

    private final String text;

    /**
     * 渡された文字列からツイートオブジェクトを生成します
     *
     * @param text ツイートを生成する文字列
     */
    public Tweet(String text) {
        if ( text == null ) {
            throw new NullPointerException("渡された文字列がnullです");
        }
        if ( text.length() > 140 ) {
            throw new IllegalArgumentException("140文字を超えています");
        }
        this.text = text;
    }

    @Override
    public String get() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null || (obj instanceof Tweet) ) {
            return false;
        }
        Tweet tweet = (Tweet) obj;
        return this.text.equals(tweet.text);
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalError(ex);
        }
    }
}
