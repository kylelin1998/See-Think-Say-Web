package web.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import web.common.util.ExceptionUtil;
import web.config.AppConfig;
import web.config.ExecutorsConfig;
import web.config.RequestProxyConfig;
import web.util.GPTUtil;
import web.util.gpt.parameter.GPTChatParameter;
import web.util.gpt.response.GPTChatResponse;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private AppConfig appConfig;

    public SseEmitter completions(String body, String authorization) {
        SseEmitter emitter = new SseEmitter();
        ExecutorsConfig.submit(() -> {
            chat(body, authorization, emitter);
        });
        return emitter;
    }

    private void chat(String body, String authorization, SseEmitter emitter) {
        try {
            GPTChatParameter parameter = JSON.parseObject(body, GPTChatParameter.class);

            RequestProxyConfig proxy = appConfig.buildProxy();
            GPTChatResponse response = GPTUtil.chat(proxy, StringUtils.replace(authorization, "Bearer ", ""), parameter, (content) -> {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name("message")
                        .data(content)
                        ;
                try {
                    emitter.send(event);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            });
            if (!response.isOk()) {
                emitter.completeWithError(new RuntimeException(JSON.toJSONString(response)));
                return;
            }
            emitter.complete();
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
            emitter.completeWithError(e);
        }
    }

}
