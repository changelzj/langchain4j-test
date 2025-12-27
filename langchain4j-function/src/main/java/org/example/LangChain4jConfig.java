package org.example;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {

    @Bean
    public StreamingChatModel streamingChatModel() {

        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://api.deepseek.com/")
                .apiKey(System.getenv("DSKEY"))
                .modelName("deepseek-chat")
                .logRequests(true)
                .logResponses(true)
                .returnThinking(true)
                .build();
    }

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
