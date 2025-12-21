package org.example.controller;

import jakarta.annotation.Resource;
import org.example.ai.StreamAssistant;
import org.example.ai.TestAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("high-chat-stream")
public class HighChatStreamController {

    @Resource
    private StreamAssistant streamAssistant;

    @Resource
    private TestAssistant testAssistant;

    @GetMapping(value = "chat", produces = "text/html; charset=utf-8")
    public Flux<String> chat(String msg, String msgId) {
        return streamAssistant.chat(msg, msgId);

    }






    @GetMapping(value = "chat2", produces = "text/html; charset=utf-8")
    public Flux<String> chat2(String msg) {
        return testAssistant.chat(msg);

    }



    @GetMapping(value = "chat3", produces = "text/html; charset=utf-8")
    public Flux<String> chat3(String msg) {
        return testAssistant.chat2(msg);

    }


}
