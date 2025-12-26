package org.example.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class EmbeddingConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        return null;
    }
}
