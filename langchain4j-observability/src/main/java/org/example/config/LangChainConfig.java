package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class LangChainConfig {

//    @Resource
//    private ChatModelListener chatModelListener;
//
//    @Bean
//    public ChatModel streamingChatModel() {
//
//        return OpenAiChatModel.builder()
//                .baseUrl("https://api.deepseek.com/")
//                .apiKey(System.getProperty("OPEN_API_KEY"))
//                .modelName("deepseek-reasoner")
//                .logRequests(true)
//                .logResponses(true)
//                .returnThinking(true)
//                .listeners(Collections.singletonList(chatModelListener))
//                .build();
//    }

}
