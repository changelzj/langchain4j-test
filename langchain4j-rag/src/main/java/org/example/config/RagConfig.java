package org.example.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
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
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class RagConfig {

    @Resource
    private EmbeddingModel embeddingModel;

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
    @Primary
    public EmbeddingStore<TextSegment> embeddingStore() {
        //UrlDocumentLoader.load()
        //FileSystemDocumentLoader.loadDocument()
        //TextDocumentParser
        //ApachePdfBoxDocumentParser
       // ApacheTikaDocumentParser
        //ApachePoiDocumentParser

        //DocumentByParagraphSplitter
       // DocumentByLineSplitter
        //DocumentBySentenceSplitter
       // DocumentByWordSplitter
        //DocumentByCharacterSplitter
       // DocumentByRegexSplitter
        //DocumentSplitters.recursive()

        List<Document> documents = ClassPathDocumentLoader.loadDocuments("document", new ApacheTikaDocumentParser());

        InMemoryEmbeddingStore embeddingStore = new InMemoryEmbeddingStore();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter() )
                .embeddingModel(embeddingModel)
                .build();

        ingestor.ingest(documents);

        return embeddingStore;

    }



    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore embeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .maxResults(7) //返回片段数
                .minScore(0.5) //最小余弦相似度
                .embeddingModel(embeddingModel)
                .build();
    }

    @Bean
    public DocumentSplitter documentSplitter() {
        return DocumentSplitters.recursive(1000, 100);
    }



}

