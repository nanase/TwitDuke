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
package net.nokok.twitduke.cli;

import java.util.Scanner;
import net.nokok.twitduke.core.auth.LambdaOAuth;
import net.nokok.twitduke.core.auth.LambdaOAuthImpl;
import net.nokok.twitduke.core.io.PropertyWriter;
import net.nokok.twitduke.core.type.AccessTokenProperty;

/**
 * コマンドラインで認証を行うクラスです
 */
public class OAuthCLI {

    /**
     * エントリポイントです。
     *
     * 渡されたオプションの先頭の文字列をファイル名としてアクセストークンを保存します
     *
     * @param args
     */
    public static void main(String[] args) {
        if ( args.length == 0 ) {
            throw new IllegalArgumentException();
        }
        new OAuthCLI().run(args[0]);
    }

    private void run(String fileName) {
        LambdaOAuth lambdaOAuth = new LambdaOAuthImpl();
        lambdaOAuth.gotRequestToken(requestToken -> {
            System.out.println("please open this url:" + requestToken.getAuthorizationURL());
            System.out.println("Enter PIN:");
            Scanner scanner = new Scanner(System.in);
            lambdaOAuth.setPin(String.valueOf(scanner.nextInt()));
        });
        lambdaOAuth.gotAccessToken(accessToken -> {
            AccessTokenProperty accessTokenProperty = new AccessTokenProperty(accessToken);
            PropertyWriter writer = new PropertyWriter(".");
            writer.write(accessTokenProperty.toProperties(), fileName);
        });
        lambdaOAuth.onException(System.out::println);
        lambdaOAuth.run();
    }

}