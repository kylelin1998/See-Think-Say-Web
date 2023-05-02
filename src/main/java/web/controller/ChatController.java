package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import web.service.ChatService;

@Controller
@RequestMapping("/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/completions")
    public SseEmitter completions(@RequestBody String body, @RequestHeader("Authorization") String authorization) {
        return chatService.completions(body, authorization);
    }

}
