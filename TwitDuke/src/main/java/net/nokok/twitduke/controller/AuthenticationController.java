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
package net.nokok.twitduke.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.nokok.twitduke.core.impl.account.AccessTokenSerializer;
import net.nokok.twitduke.core.api.account.AccessTokenWriter;
import net.nokok.twitduke.core.api.twitter.TwitterAuthentication;
import net.nokok.twitduke.core.impl.twitter.AsyncTwitterInstanceGeneratorImpl;
import net.nokok.twitduke.core.api.view.Dialog;
import net.nokok.twitduke.core.api.view.DialogResultListener;
import net.nokok.twitduke.view.OAuthDialog;
import twitter4j.AsyncTwitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * 認証処理を制御するコントローラです。
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class AuthenticationController implements TwitterAuthentication, DialogResultListener<String> {

    private final Dialog<String> dialog = new OAuthDialog();
    private RequestToken requestToken;
    private AsyncTwitter twitter;

    /**
     * 認証処理を開始します。
     * 新たにAsyncTwitterオブジェクトを生成し、RequestTokenを非同期で取得します
     */
    @Override
    public void start() {
        twitter = new AsyncTwitterInstanceGeneratorImpl().generate();
        twitter.addListener(new OAuthTwitterListener());
        dialog.setDialogResultListener(this);
        twitter.getOAuthRequestTokenAsync();
    }

    /**
     * ダイアログのCancelボタンが押されると呼ばれます。
     * 認証処理を中止し、ダイアログを破棄します。
     */
    @Override
    public void cancelButtonPushed() {
        dialog.dispose();
    }

    /**
     * ダイアログのOKボタンが押されると呼ばれます。
     * 入力されたPINとRequestTokenを用いてAccessTokenを非同期で取得します
     * <p>
     * @param o
     */
    @Override
    public void okButtonPushed(String o) {
        twitter.getOAuthAccessTokenAsync(requestToken, o);
    }

    private class OAuthTwitterListener extends TwitterAdapter {

        /**
         * getOAuthRequestTokenAsync()によってRequestTokenが取得されたら呼ばれます。
         * 認証処理を続行するためにRequestTokenに格納されている認証用のURLをデフォルトの
         * ブラウザで開きます。
         * <p>
         * @param token 取得したRequestTokenオブジェクト
         */
        @Override
        public void gotOAuthRequestToken(RequestToken token) {
            requestToken = token;
            openInBrowser(requestToken.getAuthorizationURL());
        }

        /**
         * getOAuthAccessTokenAsync(RequestToken, String)によってAccessTokenが
         * 取得されたら呼ばれます。認証処理を完了するためにAccessTokenをシリアライズして
         * ディスクに保存します。
         * <p>
         * @param token 取得したAccessTokenオブジェクト
         */
        @Override
        public void gotOAuthAccessToken(AccessToken token) {
            AccessTokenWriter writer = new AccessTokenSerializer();
            writer.writeAccessToken(token);
            dialog.dispose();
        }

        /**
         * TwitterAPIリクエスト送信中にエラーが発生するとこのメソッドが呼ばれます。
         * <p>
         * @param te
         * @param method
         */
        @Override
        public void onException(TwitterException te, TwitterMethod method) {
            throw new InternalError(te);
        }

        /**
         * 渡されたURLをデフォルトに指定されているブラウザで開きます。
         * <p>
         * @param url ブラウザで開くURL
         * <p>
         * @throws InternalError URLの文法がおかしい場合もしくはブラウザにアクセス出来なかった場合スローされます
         */
        private void openInBrowser(String url) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                dialog.show();
            } catch (IOException | URISyntaxException ex) {
                throw new InternalError(ex);
            }
        }
    }
}
