package org.example.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.*;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("memory-chat")
@Slf4j
public class MemoryController {


    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private ChatMemoryProvider chatMemoryProvider;

    //http://127.0.0.1:8080/memory-chat/chat?msg=&msgId=
    @GetMapping(value = "streaming", produces = "text/html; charset=utf-8")
    public Flux<String> streaming(String msg, String msgId) {

        ChatMemory chatMemory = chatMemoryProvider.get(msgId);
        chatMemory.add(UserMessage.from(msg));

        return Flux.create(sink -> {
            streamingChatModel.chat(chatMemory.messages(), new StreamingChatResponseHandler() {

                @Override
                public void onPartialResponse(PartialResponse partialResponse, PartialResponseContext context) {
                    sink.next(partialResponse.text());
                }

                @Override
                public void onPartialThinking(PartialThinking partialThinking) {
                    sink.next("<span style='color:red;'>" + partialThinking.text() + "</span>");
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    TokenUsage tokenUsage = completeResponse.tokenUsage();
                    log.info("token usage: {}", tokenUsage);

                    AiMessage aiMessage = completeResponse.aiMessage();
                    chatMemory.add(aiMessage);

                    sink.complete();
                }

                @Override
                public void onError(Throwable error) {
                    error.printStackTrace();
                    sink.complete();
                }
            });
        });

    }


}
