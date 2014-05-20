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
package net.nokok.twitduke;

import static com.google.common.io.ByteStreams.nullOutputStream;
import java.io.File;
import java.io.PrintStream;
import net.nokok.twitduke.core.account.AccountManager;
import net.nokok.twitduke.core.account.DirectoryHelper;
import net.nokok.twitduke.core.auth.LambdaOAuthFactory;
import net.nokok.twitduke.core.auth.OAuthRunnable;
import net.nokok.twitduke.core.factory.AccountManagerFactory;
import net.nokok.twitduke.core.factory.TwitterStreamFactory;
import net.nokok.twitduke.core.io.Paths;
import net.nokok.twitduke.core.log.ErrorLogExporter;
import net.nokok.twitduke.pluginsupport.PluginManager;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのMainクラスです。このクラスはエントリーポイントを持っています。
 *
 * このクラスがTwitDukeの起動処理を制御します。
 *
 */
public class Main {

    /**
     * TwitDukeのエントリポイントです。
     *
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        if ( !existsTwitDukeDir() ) {
            DirectoryHelper.createTwitDukeDirectories();
        }
        ErrorLogExporter logger = new ErrorLogExporter();
        System.setErr(new PrintStream(nullOutputStream()));
        System.setOut(new PrintStream(nullOutputStream()));
        final AccountManager accountManager = AccountManagerFactory.newInstance();
        if ( !accountManager.hasValidAccount() ) {
            OAuthRunnable auth = LambdaOAuthFactory.newInstance();
            auth.onError(logger::onError);
            auth.onSuccess(accountManager::addAccount);
            auth.startOAuth();
            return;
        }
        AccessToken accessToken = accountManager.readPrimaryAccount().get();
        PluginManager globalPluginMgr = new PluginManager("plugins", accessToken);
        TwitterStream twitterStream = TwitterStreamFactory.newInstance(accessToken);
        twitterStream.addListener(globalPluginMgr.getStreamEventListener());
        twitterStream.user();
    }

    private static boolean existsTwitDukeDir() {
        return new File(Paths.TWITDUKE_HOME).exists();
    }

}
