package com.mitya;

import static com.mitya.InitializerServer.sample;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.util.CharsetUtil;

public class ServerRequestHandler {

    public FullHttpResponse checkValue(String s) throws InterruptedException {
        String url = "";
        if (s.contains("%3C")) {
            url = s.substring(s.indexOf("%3C") + 3, s.length() - 3);
            s = s.substring(0, 9);
        }
        System.out.println(s);
        System.out.println(url);
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
        return new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer(sample.getPage(), CharsetUtil.UTF_8));
    }

    private FullHttpResponse valueRedirect(String url) {
        FullHttpResponse r = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
        r.headers().set(HttpHeaders.Names.LOCATION, url);
        return r;
    }

    private FullHttpResponse notFoundValue() {
        return new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer("<p>Not Found</p>", CharsetUtil.UTF_8));
    }
}
