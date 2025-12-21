package org.example;


import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ChatModel chatModel = OpenAiChatModel.builder()
                .baseUrl("https://api.gptsapi.net/v1")
                .apiKey(System.getProperty("OPEN_API_KEY"))
                .modelName("gpt-4.1")
                .build();

        List<ChatMessage> messages = Arrays.asList(
                new SystemMessage("你是一个数学老师，用简单易懂的方式解释数学概念。"),
                new UserMessage("什么是微积分？")
        );

        ChatResponse chatResponse = chatModel.chat(messages);
        System.out.println(chatResponse);

    }
}




