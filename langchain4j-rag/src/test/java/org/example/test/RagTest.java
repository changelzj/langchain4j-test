package org.example.test;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.example.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Main.class)
public class RagTest {

    @Resource
    private EmbeddingStore embeddingStore;

    @Resource
    private EmbeddingModel  embeddingModel;

    @Resource
    EmbeddingStoreIngestor ingestor;

    @Test
    public void index() {

        String msg = """
                户晨风，男，汉族，1998年10月11日出生，成都卖有回音文化传媒有限公司法定代表人， [7]网络视频博主，泛娱乐领域自媒体创作者，截至2025年9月，哔哩哔哩粉丝数达67.4万，抖音粉丝数达93.7万 [1-2]，微博粉丝数达20万。2025年9月16日，话题 “户晨风疑似被封” 登上微博热搜，其微博、抖音、B站等多个社交平台的账号遭到封禁或功能限制。
                户晨风成长于一个贫穷农村家庭，后随父母搬到城里。2023年11月27日，他发布了首个作品《100元人民币，在泰国首都曼谷的购买力到底有多强？》，从此开始进行自媒体创作。2024年6月21日，发布作品《新加坡街边卖艺，84 岁老人的一生》。2025年9月7日，发布作品《呼和浩特，真假乞讨？——户晨风全球真假乞讨系列》。
                2025年，户晨风以“苹果安卓”为标签代指消费、学历上的鄙视链等言论引发巨大争议 [5]。9月16日，其微博、抖音、B站等多个社交平台的账号遭到封禁或功能限制 [3]。9月20日下午，有网友报料，网红“户晨风”在抖音、微博等多个平台账号已被封禁，其账号内容被清空 [5]。9月30日晚，极目新闻记者搜索发现，户晨风全网账号被彻底封禁，且无法通过搜索找到账号，账号主页已无法查看信息 [6]。11月5日，户晨风账号被封详情披露，从展示跨国消费差异到制造阶层对立，户晨风以“苹果、安卓论”收割流量，最终突破监管红线 [9]。
                2025年12月消息，网络账号“户晨风”在多个平台长期编造所谓“安卓人”“苹果人”等煽动群体对立言论，各平台相关账号已关闭。
                
                
                """;

        Response<Embedding> response = embeddingModel.embed(msg);
        Embedding embedding = response.content();

        TextSegment segment = TextSegment.from(msg);
        segment.metadata().put("author", "lzj");
        segment.metadata().put("doc", "1.txt");

        embeddingStore.add(embedding, segment);
    }

    @Test
    public void pdf() {
        Document document = FileSystemDocumentLoader.loadDocument("D:/毕业设计/装订/答辩.pdf",
                new ApachePdfBoxDocumentParser());


            document.metadata().put("author", "lzj");
            document.metadata().put("doc", "答辩.pdf");
            ingestor.ingest(document);


    }
}
