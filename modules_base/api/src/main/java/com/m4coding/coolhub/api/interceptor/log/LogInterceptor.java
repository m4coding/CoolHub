package com.m4coding.coolhub.api.interceptor.log;

import com.m4coding.coolhub.api.interceptor.log.formatter.JSONFormatter;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络log打印 (规范输出)
 */
public class LogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final Logger logger;

    public LogInterceptor() {
        this(new Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(Platform.WARN, message, null);
            }
        });
    }

    public LogInterceptor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final int id = ID_GENERATOR.incrementAndGet();

        StringBuilder logBuilderOut = new StringBuilder();
        logBuilderOut.append("\n----------------------------------------------------------------------------------------------------------------------------------\n");

        //请求打印
        {
            final String LOG_PREFIX = "[" + id + " request]";
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            logBuilderOut.append(LOG_PREFIX).append(requestStartMessage).append("\n");

            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logBuilderOut.append(LOG_PREFIX).append("Content-Type: ").append(requestBody.contentType()).append("\n");
                }
                if (requestBody.contentLength() != -1) {
                    logBuilderOut.append(LOG_PREFIX).append("Content-Length: ").append(requestBody.contentLength()).append("\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logBuilderOut.append(LOG_PREFIX).append(name).append(": ").append(headers.value(i)).append("\n");
                }
            }

            if (!hasRequestBody) {
                logBuilderOut.append(LOG_PREFIX).append("--> END ").append(request.method()).append("\n");
            } else if (bodyEncoded(request.headers())) {
                logBuilderOut.append(LOG_PREFIX).append("--> END ")
                        .append(request.method())
                        .append(" (encoded body omitted)").append("\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    final String bufferString = buffer.readString(charset);
                    if (contentType != null && "json".equals(contentType.subtype())) {
                        logBuilderOut.append(LOG_PREFIX).append("\n").append(JSONFormatter.formatJSON(bufferString)).append("\n");
                    } else {
                        logBuilderOut.append(LOG_PREFIX).append(bufferString).append("\n");
                    }
                    logBuilderOut.append(LOG_PREFIX)
                            .append("--> END ")
                            .append(request.method())
                            .append(" (")
                            .append(requestBody.contentLength())
                            .append("-byte body)")
                            .append("\n");
                } else {
                    logBuilderOut.append(LOG_PREFIX)
                            .append("--> END ")
                            .append(request.method())
                            .append(" (binary ")
                            .append(requestBody.contentLength())
                            .append("-byte body omitted)")
                            .append("\n");
                }
            }
        }

        //响应打印
        {
            logBuilderOut.append("\n");

            final String LOG_PREFIX = "[" + id + " response]";
            long startNs = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                logBuilderOut.append(LOG_PREFIX).append("<-- HTTP FAILED: ").append(e).append("\n");
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            logBuilderOut.append(LOG_PREFIX)
                    .append("<-- ")
                    .append(response.code())
                    .append(' ')
                    .append(response.message())
                    .append(' ')
                    .append(response.request().url())
                    .append(" (")
                    .append(tookMs)
                    .append("ms")
                    .append(')')
                    .append("\n");

            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logBuilderOut.append(LOG_PREFIX)
                        .append(headers.name(i))
                        .append(": ")
                        .append(headers.value(i)).append("\n");
            }

            if (!HttpHeaders.hasBody(response)) {
                logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP").append("\n");
            } else if (bodyEncoded(response.headers())) {
                logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP (encoded body omitted)").append("\n");
            } else if (contentLength > 1024) {
                logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP (body too big)").append("\n");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        logBuilderOut.append(LOG_PREFIX).append("Couldn't decode the response body; charset is likely malformed.").append("\n");
                        logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP").append("\n");
                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP (binary ").append(buffer.size()).append("-byte body omitted)").append("\n");
                    return response;
                }

                if (contentLength != 0) {
                    final String bufferString = buffer.clone().readString(charset);
                    if (contentType != null && "json".equals(contentType.subtype())) {
                        logBuilderOut.append(LOG_PREFIX).append("\n").append(JSONFormatter.formatJSON(bufferString)).append("\n");
                    } else {
                        logBuilderOut.append(LOG_PREFIX).append(bufferString).append("\n");
                    }
                }

                logBuilderOut.append(LOG_PREFIX).append("<-- END HTTP (").append(buffer.size()).append("-byte body)").append("\n");
            }

            logBuilderOut.append("----------------------------------------------------------------------------------------------------------------------------------\n");
            logger.log(logBuilderOut.toString());

            return response;
        }
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public interface Logger {
        void log(String message);
    }
}
