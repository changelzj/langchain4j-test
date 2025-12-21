package org.example.controller;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {

/*

*/

    @Resource(name = "deepseek")
    private ChatModel chatModel;


    @GetMapping("chat")
    public String chat(String msg) {

        List<ChatMessage> messages = Arrays.asList(
                SystemMessage.from("你是一个数学老师，用简单易懂的方式解释数学概念。"),
                UserMessage.from(msg)
        );

        ChatResponse chatResponse = chatModel.chat(messages);
        TokenUsage tokenUsage = chatResponse.tokenUsage();
        log.info("token usage: {}", tokenUsage);

        return chatResponse.aiMessage().text();

    }






}







