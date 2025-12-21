package org.example.controller;

import jakarta.annotation.Resource;
import org.example.ai.AiAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("high-chat")
public class HighChatController {

    //@Resource
    private AiAssistant aiAssistant;

    @GetMapping("chat")
    public String chat(String msg) {
        return aiAssistant.chat(msg);

    }
}
