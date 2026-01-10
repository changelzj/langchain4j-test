package org.example.test;

import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@SpringBootTest(classes = Main.class)
@Slf4j
public class MTest {

    @Resource
    private ChatModel chatModel;

    @Value("classpath:image.png")
    private org.springframework.core.io.Resource resource;

    @Test
    public void imageToText() throws IOException {

        byte[] byteArray = resource.getContentAsByteArray();
        String base64 = Base64.getEncoder().encodeToString(byteArray);

        UserMessage userMessage = UserMessage.from(
                TextContent.from("图片中的统计数据是谁发布的，大学学历网民占比是多少。"),
                ImageContent.from(base64, "image/png")
        );

        ChatResponse chatResponse = chatModel.chat(List.of(userMessage));
        log.info("******** chatResponse: {}", chatResponse);


    }
}
