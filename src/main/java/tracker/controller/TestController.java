package tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tracker.client.BlueskyApiClient;
import tracker.processor.BlueskyDataProcessor;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;

    public TestController(BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
    }

    // ブラウザから http://localhost:8080/api/v1/test/fetch?q=キーワード で呼び出せる
    @GetMapping("/fetch")
    public String testFetch(@RequestParam() String q) {
        try {
            // 1. APIから取得
            String json = apiClient.fetchFeedJson(q, null);
            
            // 2. DBへ保存（既存のプロセッサを使用）
            dataProcessor.processFeed(json);
            
            return "成功！キーワード 「" + q + "」 のデータを取得し、DBに保存しました。";
        } catch (Exception e) {
            return "エラーが発生しました: " + e.getMessage();
        }
    }
}

