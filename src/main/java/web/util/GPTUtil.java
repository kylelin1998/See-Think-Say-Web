package web.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.util.Timeout;
import web.common.util.ExceptionUtil;
import web.config.RequestProxyConfig;
import web.util.gpt.*;
import web.util.gpt.parameter.*;
import web.util.gpt.response.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class GPTUtil {

    public static GPTChatResponse chat(RequestProxyConfig requestProxyConfig, String token, GPTChatParameter parameter, GPTCallback callback) {
        GPTChatResponse chatResponse = new GPTChatResponse();
        chatResponse.setOk(false);

        try {
            Request request = Request
                    .post("https://api.openai.com/v1/chat/completions")
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("accept", "text/event-stream")
                    .bodyString(JSON.toJSONString(parameter), org.apache.hc.core5.http.ContentType.APPLICATION_JSON)
                    .connectTimeout(Timeout.ofSeconds(30))
                    .responseTimeout(Timeout.ofSeconds(30))
                    ;
            requestProxyConfig.viaProxy(request);
            Response response = request.execute();

            StringBuilder responseBuilder = new StringBuilder();
            int code = response.handleResponse((classicHttpResponse) -> {
                InputStream inputStream = classicHttpResponse.getEntity().getContent();

                try (BufferedInputStream in = IOUtils.buffer(inputStream)) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                        String line = null;
                        while((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                            String s = StringUtils.substringAfter(line, "data: ");
                            if (!s.equals("")) {
                                callback.call(s);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
                }

                return classicHttpResponse.getCode();
            });

            chatResponse.setStatusCode(code);

            chatResponse.setOk(code == 200);
            chatResponse.setResponse(responseBuilder.toString());
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
        }

        return chatResponse;
    }

}
