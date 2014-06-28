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
package net.nokok.twitduke.core.thirdpartyservice.shindanmaker;

import java.util.Optional;
import java.util.Properties;
import net.nokok.twitduke.core.io.Paths;
import net.nokok.twitduke.core.io.PropertyReader;

public class ShindanmakerProperty {

    private final Properties properties;

    public ShindanmakerProperty() {
        this.properties = new PropertyReader(Paths.SHINDANMAKER_CONFIG_FILE)
                .read().orElseThrow(() -> new RuntimeException(Paths.SHINDANMAKER_CONFIG_FILE + "が見つかりません"));
        if ( properties.getProperty("name") == null ) {
            throw new RuntimeException("プロパティにnameが指定されていません");
        }
    }

    public Optional<String> name() {
        return Optional.ofNullable(properties.getProperty("name"));
    }
}