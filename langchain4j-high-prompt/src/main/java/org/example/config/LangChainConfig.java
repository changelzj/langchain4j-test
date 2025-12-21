package org.example.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.example.ai.AiAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChainConfig {

//    @Bean
//    public ChatModel deepseek() {
//
//
//        return OpenAiChatModel.builder()
//                .baseUrl("https://api.deepseek.com/")
//                .apiKey(System.getProperty("OPEN_API_KEY"))
//                .modelName("deepseek-reasoner")
//                .maxRetries(3)
//                .logRequests(true)
//                .logResponses(true)
//                .returnThinking(true)
//                .build();
//    }

    @Bean
    public StreamingChatModel streamingChatModel() {


        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://api.deepseek.com/")
                .apiKey(System.getProperty("OPEN_API_KEY"))
                .modelName("deepseek-reasoner")
                .logRequests(true)
                .logResponses(true)
                .returnThinking(true)
                .build();
    }

    @Bean
    public AiAssistant aiAssistant(ChatModel chatModel) {
        return AiServices.builder(AiAssistant.class)
                .chatModel(chatModel)
                .build();
    }

//    @Bean
//    public AiAssistant aiAssistant2(ChatModel chatModel) {
//        return AiServices.create(AiAssistant.class, chatModel);
//    }


    @Bean
    public ChatMemoryStore chatMemoryStore() {
        return new InMemoryChatMemoryStore();
    }



    @Bean
    public ChatMemoryProvider chatMemoryProvider () {
        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object id) {
                return MessageWindowChatMemory.builder()
                        .id(id)
                        .maxMessages(1000)
                        .chatMemoryStore( chatMemoryStore() )
                        .build();
            }
        };
    }



}


