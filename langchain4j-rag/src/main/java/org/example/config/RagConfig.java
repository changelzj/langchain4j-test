package org.example.config;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.*;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

import jakarta.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RagConfig {

    @Resource
    private EmbeddingModel embeddingModel;

    @Bean
    public StreamingChatModel streamingChatModel() {

        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://api.deepseek.com/")
                .apiKey(System.getenv("DSKEY"))
                .modelName("deepseek-reasoner")
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


    @Bean
    public EmbeddingStoreIngestor ingestor() {
        return EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore() )
                .documentSplitter( DocumentSplitters.recursive(1000, 100) ) //指定分割器
                .embeddingModel(embeddingModel)
                .build();
    }


    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore embeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .maxResults(7) //返回片段数
                .minScore(0.5) //最小余弦相似度
                .embeddingModel(embeddingModel) //使用自己定义的向量模型
                .dynamicFilter(query -> {
                    Object chatMemoryId = query.metadata().chatMemoryId();

                    String userId = chatMemoryId.toString();
                    return MetadataFilterBuilder.metadataKey("author").isEqualTo(userId);
                })
                .build();
    }



    @Bean
    @Primary
    public EmbeddingStore<TextSegment> embeddingStore() {

        return QdrantEmbeddingStore.builder()
                .host("192.168.228.104")
                .port(6334)
                .collectionName("test-qdrant")
                .build();

    }
}

