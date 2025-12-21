package org.example.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Resource
    private ChatModel chatModel;

    @GetMapping("chat")
    public String chat(String msg) {
        return chatModel.chat(msg);
    }
}


