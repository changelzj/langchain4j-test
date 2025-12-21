package org.example.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class EmbeddingConfig {

    public EmbeddingModel embeddingModel() {
        return null;
    }
}
