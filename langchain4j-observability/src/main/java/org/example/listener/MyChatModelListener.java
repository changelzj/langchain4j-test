package org.example.listener;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class MyChatModelListener implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        String traceId = UUID.randomUUID().toString();
        requestContext.attributes().put("traceId", traceId);
        log.info("********** 请求参数 {} {} ********", traceId, requestContext);
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        Object traceId = responseContext.attributes().get("traceId");
        log.info("********** 响应结果 {} {} ********", traceId, responseContext);
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.info("********** 请求异常 {}  ********", errorContext );
    }
}
