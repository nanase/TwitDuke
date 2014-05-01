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
package net.nokok.twitduke.core.impl.account;

import java.util.ArrayList;
import java.util.Optional;
import net.nokok.twitduke.core.api.account.AccessTokenReader;
import net.nokok.twitduke.core.api.account.AccessTokenWriter;
import net.nokok.twitduke.core.api.account.AccountManager;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのアカウントに関する操作をするクラスです。
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class AccountManagerImpl implements AccountManager {

    @Override
    public ArrayList<Optional<AccessToken>> getAccessTokenList() {
        AccessTokenReader accessTokenReader = new AccessTokenDeserializer();
        return accessTokenReader.getAccessTokenList();
    }

    @Override
    public boolean hasValidAccount() {
        AccessTokenReader reader = new AccessTokenDeserializer();
        return reader.readFirstAccessToken().isPresent();
    }

    @Override
    public Optional<AccessToken> readFirstAccessToken() {
        AccessTokenReader accessTokenReader = new AccessTokenDeserializer();
        return accessTokenReader.readFirstAccessToken();
    }

    @Override
    public void writeAccessToken(AccessToken accessToken) {
        AccessTokenWriter accessTokenWriter = new AccessTokenSerializer();
        accessTokenWriter.writeAccessToken(accessToken);
    }
}