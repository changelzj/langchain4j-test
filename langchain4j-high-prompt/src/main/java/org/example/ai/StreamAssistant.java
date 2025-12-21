package org.example.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "streamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface StreamAssistant {

    @SystemMessage("你是一个数学老师，用简单易懂的方式解释数学概念。")
    Flux<String> chat(@UserMessage String prompt, @MemoryId String msgId);


}
