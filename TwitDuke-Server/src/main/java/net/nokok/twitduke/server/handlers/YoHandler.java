package net.nokok.twitduke.server.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.nokok.twitduke.core.factory.TAsyncTwitterFactory;
import net.nokok.twitduke.core.type.Retrievable;
import org.mortbay.jetty.Handler;
import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

public class YoHandler implements Retrievable<Handler> {

    private final AsyncTwitter asyncTwitter;
    private final AbstractGetRequestHandler handler = new AbstractGetRequestHandler("/v1/Yo") {

        @Override
        public void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            asyncTwitter.updateStatus(StringUtil.appendRandomWhitespace("Yo"));
            sendOK();
        }
    };

    public YoHandler(AccessToken accessToken) {
        this.asyncTwitter = TAsyncTwitterFactory.newInstance(accessToken);
    }

    @Override
    public Handler get() {
        return handler;
    }

}
