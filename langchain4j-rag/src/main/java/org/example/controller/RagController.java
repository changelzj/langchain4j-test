package org.example.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.example.ai.RagAssistance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("rag-chat")
public class RagController {

    @Resource
    private RagAssistance assistance;




    @GetMapping(value = "stream", produces = "text/html; charset=utf-8")
    public Flux<String> stream(String msg, String msgId) {
        return assistance.chat(msg, msgId);

    }


}



