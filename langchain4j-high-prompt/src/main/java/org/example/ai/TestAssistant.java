package org.example.ai;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "streamingChatModel"
        //chatMemoryProvider = "chatMemoryProvider"
)
public interface TestAssistant {
    @UserMessage("你是一个数学老师，用简单易懂的方式解释数学概念。{{msg}}")
    Flux<String> chat(@V("msg") String prompt);

    @UserMessage("你是一个数学老师，用简单易懂的方式解释数学概念。{{it}}")
    Flux<String> chat2(String prompt);

}
