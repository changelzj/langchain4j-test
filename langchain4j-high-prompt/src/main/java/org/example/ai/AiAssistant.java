package org.example.ai;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

//@AiService(
        //如需手动配置模型，需要设置属性：AiServiceWiringMode.EXPLICIT
        //wiringMode = AiServiceWiringMode.EXPLICIT,
        //如需手动配置模型，要指定具体使用哪个模型，例如：chatModel = "deepseek"
        //chatModel = "deepseek"
//)
public interface AiAssistant {

    //@SystemMessage("")
    String chat(String prompt);

}


