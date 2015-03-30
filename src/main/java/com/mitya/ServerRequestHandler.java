package com.mitya;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.util.CharsetUtil;

public class ServerRequestHandler {

    public FullHttpResponse checkValue(String s) throws InterruptedException {
        String url = "";
        if (s.contains("%3C")) {
            url = s.substring(s.indexOf("%3C") + 3, s.length() - 3);
            s = s.substring(1, 9);
        }
        switch (s) {
            case "/hello":
                return valueHelloWorld();
            case "/status":
                return valueStatus();
            case "/redirect":
                return valueRedirect(url);
            default:
                return notFoundValue();
        }
    }

    private FullHttpResponse valueHelloWorld() throws InterruptedException {
        Thread.sleep(10 * 1000);
        return new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer("<p>Hello world</p>", CharsetUtil.UTF_8));
    }

    private FullHttpResponse valueStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private FullHttpResponse valueRedirect(String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private FullHttpResponse notFoundValue() {
        return new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer("<p>Not Found", CharsetUtil.UTF_8));
    }
}
