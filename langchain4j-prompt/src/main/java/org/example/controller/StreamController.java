package org.example.controller;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;

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
@RequestMapping("chat")
@Slf4j
public class StreamController {

    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping(value = "streaming", produces = "text/html; charset=utf-8")
    public Flux<String> streaming(String msg) {

        List<ChatMessage> messages = Arrays.asList(
                SystemMessage.from("你是一个数学老师，用简单易懂的方式解释数学概念。"),
                UserMessage.from(msg)
        );

        return Flux.create(sink -> {
            streamingChatModel.chat(messages, new StreamingChatResponseHandler() {

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
