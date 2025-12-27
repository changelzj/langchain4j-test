package org.example.controller;

import jakarta.annotation.Resource;
import org.example.ai.assistant.ToolAssistant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("ai")
public class ChatController {

    @Resource
    private ToolAssistant toolAssistant;

    @GetMapping(value = "agent-stream", produces = "text/html;charset=utf-8")
    public Flux<String> agent(String msg, String chatId) {
        return toolAssistant.chat(msg, chatId);

    }


}


